package remote.com.example.huangli.punchcard.fragment;

import android.content.Context;
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
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.ctviews.ListViewForScrollView;
import remote.com.example.huangli.punchcard.model.Task;
import remote.com.example.huangli.punchcard.model.User;
import remote.com.example.huangli.punchcard.utils.ToastUtils;

/**
 * Created by huangli on 16/6/19.
 */
public class TaskFragment extends Fragment{
    private ListViewForScrollView listView;
    private ProgressBar progressBar;
    private Button btnPunch;
    private TextView tvProgress;
    private ItemListviewTaskAdapter itemListviewTaskAdapter;
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
    }

    @Override
    public void onResume() {
        super.onResume();
        itemListviewTaskAdapter.notifyDataSetChanged();
    }

    private void initUi() {
        itemListviewTaskAdapter = new ItemListviewTaskAdapter(getActivity());
        listView.setAdapter(itemListviewTaskAdapter);
        itemListviewTaskAdapter.setData(User.getInstance().getCurPlan().getTasks());
    }

    private void findviews(View view){
        listView = (ListViewForScrollView)view.findViewById(R.id.listview_tasks);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_horizontal);
        btnPunch = (Button)view.findViewById(R.id.btn_punchcard);
        tvProgress = (TextView)view.findViewById(R.id.tv_progress);
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
            holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    object.setComplated(isChecked);
                    int i = 0;
                    for (Task task : objects){
                        if (task.isComplated()){
                            i++;
                        }
                    }
                    int progress = (int) (100*((float)i/(float)objects.size()));
                    tvProgress.setText(progress+"%");
                    progressBar.setProgress(progress);
                }
            });
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
