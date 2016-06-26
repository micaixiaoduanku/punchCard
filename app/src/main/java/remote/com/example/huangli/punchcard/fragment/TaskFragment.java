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
import java.util.List;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.activity.SharePicsPagerActivity;
import remote.com.example.huangli.punchcard.adpter.SharePicGridAdpter;
import remote.com.example.huangli.punchcard.ctviews.ListViewForScrollView;
import remote.com.example.huangli.punchcard.model.Card;
import remote.com.example.huangli.punchcard.model.Plan;
import remote.com.example.huangli.punchcard.model.Task;
import remote.com.example.huangli.punchcard.model.User;
import remote.com.example.huangli.punchcard.server.Server;
import remote.com.example.huangli.punchcard.utils.ToastUtils;

/**
 * Created by huangli on 16/6/19.
 */
public class TaskFragment extends Fragment{
    private final String TAG = "TaskFragment";
    private ListViewForScrollView listView;
    private Button btnPunch;
    private ItemListviewTaskAdapter itemListviewTaskAdapter;
    public static SharePicGridAdpter sharePicGridAdpter;
    private GridView gridView;

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
                Server.worldTasks.add(card);
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
    }

    private void findviews(View view){
        listView = (ListViewForScrollView)view.findViewById(R.id.listview_tasks);
        gridView = (GridView)view.findViewById(R.id.grid_pics);
        btnPunch = (Button)view.findViewById(R.id.btn_punchcard);
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
