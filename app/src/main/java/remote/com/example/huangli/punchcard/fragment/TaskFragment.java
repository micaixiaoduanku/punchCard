package remote.com.example.huangli.punchcard.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.activity.SharePicsPagerActivity;
import remote.com.example.huangli.punchcard.adpter.SharePicGridAdpter;
import remote.com.example.huangli.punchcard.control.PicControler;
import remote.com.example.huangli.punchcard.ctviews.ListViewForScrollView;
import remote.com.example.huangli.punchcard.dialog.ListDialog;
import remote.com.example.huangli.punchcard.model.Card;
import remote.com.example.huangli.punchcard.model.PicsShare;
import remote.com.example.huangli.punchcard.model.Plan;
import remote.com.example.huangli.punchcard.model.Task;
import remote.com.example.huangli.punchcard.model.User;
import remote.com.example.huangli.punchcard.db.DbServer;
import remote.com.example.huangli.punchcard.utils.BitmapUtils;
import remote.com.example.huangli.punchcard.utils.PCLog;
import remote.com.example.huangli.punchcard.utils.ToastUtils;

/**
 * Created by huangli on 16/6/19.
 */
public class TaskFragment extends Fragment implements PicControler.PicActionListener{
    private final String TAG = "TaskFragment";
    private ListViewForScrollView listView;
    private Button btnPunch;
    private ItemListviewTaskAdapter itemListviewTaskAdapter;
    public static SharePicGridAdpter sharePicGridAdpter;
    private GridView gridView;
    private ListDialog listDialog;

    private final int COMPRESS_SIZE_WIDTH = 720;
    private final int COMPRESS_SIZE_HEIGHT = 1280;

    public static final int REQUEST_CODE_PICK_IMAGE = 0;
    public static final int REQUEST_CODE_CAPTURE_CAMEIA =1;
    public static final int RESULT_DELETE_PICS = 2;

    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        findviews(view);
        setListeners();
        initUi();
        return view;
    }

    private void setListeners() {
        btnPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Task task : User.getInstance().getCurPlan().getTasks()){
                    if (!task.isComplated()){
                        ToastUtils.showShortToast(getActivity(),R.string.toast_no_allow);
                        return;
                    }
                }
                ToastUtils.showShortToast(getActivity(),R.string.toast_task_complated);
                Plan plan = User.getInstance().getCurPlan();
                String type = "";
                switch (plan.getType()){
                    case Plan.TYPE_100_DAYS:
                        type = getString(R.string.type_plan_100days);
                        break;
                    case Plan.TYPE_MONTH:
                        type = getString(R.string.type_plan_month);
                        break;
                    case Plan.TYPE_WEEK:
                        type = getString(R.string.type_plan_week);
                        break;
                }
                Card card = new Card(User.getInstance().getNickname(),type,plan.getDescribe(),plan.getTasks());
                DbServer.getInstance(getActivity()).insertCardToDb(card);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isChecked = ((CheckBox)view.findViewById(R.id.checkbox)).isChecked();
                ((CheckBox)view.findViewById(R.id.checkbox)).setChecked(!isChecked);
                User.getInstance().getCurPlan().getTasks().get(position).setComplated(!isChecked);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //just for test
                if (sharePicGridAdpter.getPicsSize() - 1 == position && sharePicGridAdpter.isaddBtn(position)) {
                    listDialog.show();
                } else {
                    Intent intent = new Intent(getActivity(), SharePicsPagerActivity.class);
                    intent.putExtra("position", position);
//                    ArrayList<PicsShare> shares = sharePrizePicGridAdpter.getContentPicsShares();
//                    intent.putExtra("picsshares",(Serializable)shares );
                    startActivityForResult(intent, RESULT_DELETE_PICS);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (data != null){
                Uri uri = data.getData();
                if (uri != null && !uri.equals("")){
                    //拿到图片后需要进行压缩
                    Uri compressUri = BitmapUtils.compressBitmapFromUri(getActivity(), uri, COMPRESS_SIZE_WIDTH, COMPRESS_SIZE_HEIGHT);
                    if (compressUri != null){
                        sharePicGridAdpter.addPic(new PicsShare(BitmapUtils.getBitmapFromUri(getActivity(),compressUri),false));
                        sharePicGridAdpter.notifyDataSetChanged();
                    }
                }
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            receivePicFromCamera();
        } else if (requestCode == RESULT_DELETE_PICS) {
            PCLog.i(TAG+"result delete pics ");
            if (data != null){
                PCLog.i(TAG+" data != null ");
                ArrayList<Integer> picpositions = data.getIntegerArrayListExtra("picpositions");
                if (picpositions != null){
                    for (Integer i : picpositions){
                        PCLog.i(TAG+"删除图片 position "+i);
                    }
                    sharePicGridAdpter.deletePics(picpositions);
                }
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        itemListviewTaskAdapter.notifyDataSetChanged();
    }

    private void initUi() {
        sharePicGridAdpter = new SharePicGridAdpter(getActivity());
        gridView.setAdapter(sharePicGridAdpter);
        itemListviewTaskAdapter = new ItemListviewTaskAdapter(getActivity());
        listView.setAdapter(itemListviewTaskAdapter);
        itemListviewTaskAdapter.setData(User.getInstance().getCurPlan().getTasks());
        listDialog = new ListDialog(getActivity(), new ListDialog.OnBtnClickListener() {
            @Override
            public void btnAlbumClick() {
                getImageFromAlbum();
            }

            @Override
            public void btnCameraClick() {
                getImageFromCamera();
            }

            @Override
            public void btnCancelClick() {

            }
        });
    }

    private void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    private Uri cameiaImageUri;

    private void getImageFromCamera() {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            cameiaImageUri = getActivity().getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameiaImageUri);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
        } catch (Exception e){
            PCLog.i(e.getLocalizedMessage());
            //crash at 6.0 when Permission Denial
            // java.lang.SecurityException: Permission Denial: writing com.android.providers.media.MediaProvider uri
            // content://media/external/images/media from pid=7318, uid=10199 requires android.permission.WRITE_EXTERNAL_STORAGE, or grantUriPermission()
        }

    }

    private void receivePicFromCamera(){
        try {
            ExifInterface ei = new ExifInterface(getRealPathFromURI(cameiaImageUri));
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap realBitamp,verticalBitmap;
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    realBitamp = BitmapUtils.decodeFromFile(new File(getRealPathFromURI(cameiaImageUri)),COMPRESS_SIZE_HEIGHT,COMPRESS_SIZE_WIDTH);
                    verticalBitmap = BitmapUtils.rotateImage(realBitamp,90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    realBitamp = BitmapUtils.decodeFromFile(new File(getRealPathFromURI(cameiaImageUri)),COMPRESS_SIZE_WIDTH,COMPRESS_SIZE_HEIGHT);
                    verticalBitmap = BitmapUtils.rotateImage(realBitamp,180);
                    break;
                default:
                    realBitamp = BitmapUtils.decodeFromFile(new File(getRealPathFromURI(cameiaImageUri)),COMPRESS_SIZE_WIDTH,COMPRESS_SIZE_HEIGHT);
                    verticalBitmap = realBitamp;
                    break;
            }
            PCLog.i(TAG + "摄像头返回图片 Width " + realBitamp.getWidth() + " Height " + realBitamp.getHeight());
            PCLog.i(TAG+"旋转处理后图片 Width "+ verticalBitmap.getWidth()+" Height "+verticalBitmap.getHeight());
            sharePicGridAdpter.addPic(new PicsShare(verticalBitmap, false));
            sharePicGridAdpter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    private void findviews(View view){
        listView = (ListViewForScrollView)view.findViewById(R.id.listview_tasks);
        gridView = (GridView)view.findViewById(R.id.grid_pics);
        btnPunch = (Button)view.findViewById(R.id.btn_punchcard);
    }

    @Override
    public void addPic(PicsShare picsShare) {
        sharePicGridAdpter.addPic(picsShare);
        sharePicGridAdpter.notifyDataSetChanged();
    }

    @Override
    public void deletePic(ArrayList<Integer> picpositions) {
        sharePicGridAdpter.deletePics(picpositions);
    }

    class ItemListviewTaskAdapter extends BaseAdapter {

        private List<Task> objects = new ArrayList<>();

        private Context context;
        private LayoutInflater layoutInflater;

        public ItemListviewTaskAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        public void setData(List<Task> objects){
            this.objects = objects;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Task getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_listview_task, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(position,(Task)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(int position,final Task object, ViewHolder holder) {
            //TODO implement
            holder.tvTask.setText(getString(R.string.task_tips)+(position+1)+" : "+object.getDescribe());
            holder.checkbox.setChecked(object.isComplated());
        }

        protected class ViewHolder {
            private TextView tvTask;
            private CheckBox checkbox;

            public ViewHolder(View view) {
                tvTask = (TextView) view.findViewById(R.id.tv_task);
                checkbox = (CheckBox) view.findViewById(R.id.checkbox);
                checkbox.setClickable(false);
                checkbox.setFocusable(false);
            }
        }
    }
}
