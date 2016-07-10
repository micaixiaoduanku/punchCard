package remote.com.example.huangli.punchcard.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import remote.com.example.huangli.punchcard.MainApp;
import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.adpter.SharePicGridAdpter;
import remote.com.example.huangli.punchcard.control.PicControler;
import remote.com.example.huangli.punchcard.ctviews.ListViewForScrollView;
import remote.com.example.huangli.punchcard.dialog.ListDialog;
import remote.com.example.huangli.punchcard.http.HttpProtocol;
import remote.com.example.huangli.punchcard.http.Network;
import remote.com.example.huangli.punchcard.model.PicsShare;
import remote.com.example.huangli.punchcard.model.Task;
import remote.com.example.huangli.punchcard.pojo.Pojo_Cur_Plan;
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

    private PicControler picControler;

    private Pojo_Cur_Plan pojo_cur_plan;

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
        requestData();
        return view;
    }

    private void requestData(){
        HashMap<String ,Object> map = new HashMap<>();
        map.put("account", MainApp.user.account);
        map.put("plannum", "0");
        Network.get(getActivity()).asyncPost(HttpProtocol.URLS.REQUEST_CUR_PLAN, map, new Network.JsonCallBack<Pojo_Cur_Plan>() {
            @Override
            public void onSuccess(Pojo_Cur_Plan pojo_cur_plan) {
                TaskFragment.this.pojo_cur_plan = pojo_cur_plan;
            }

            @Override
            public void onFailed(int code, String message, Exception e) {
                ToastUtils.showShortToast(getActivity(),message);
            }

            @Override
            public Class<Pojo_Cur_Plan> getObjectClass() {
                return Pojo_Cur_Plan.class;
            }
        });
    }

    private void setListeners() {
        btnPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast(getActivity(),R.string.toast_no_allow);
                ToastUtils.showShortToast(getActivity(),R.string.toast_task_complated);
//                String type = "";
//                switch (plan.getType()){
//                    case Plan.TYPE_100_DAYS:
//                        type = getString(R.string.type_plan_100days);
//                        break;
//                    case Plan.TYPE_MONTH:
//                        type = getString(R.string.type_plan_month);
//                        break;
//                    case Plan.TYPE_WEEK:
//                        type = getString(R.string.type_plan_week);
//                        break;
//                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isChecked = ((CheckBox)view.findViewById(R.id.checkbox)).isChecked();
                ((CheckBox)view.findViewById(R.id.checkbox)).setChecked(!isChecked);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //just for test
                if (sharePicGridAdpter.getPicsSize() - 1 == position && sharePicGridAdpter.isaddBtn(position)) {
                    listDialog.show();
                } else {
                    picControler.showPicInActivity(position);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        picControler.onActivityResult(requestCode, resultCode, data);
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
        listDialog = new ListDialog(getActivity(), new ListDialog.OnBtnClickListener() {
            @Override
            public void btnAlbumClick() {
                picControler.getImageFromAlbum();
            }

            @Override
            public void btnCameraClick() {
                picControler.getImageFromCamera();
            }

            @Override
            public void btnCancelClick() {

            }
        });
        picControler = new PicControler(this,this);
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
