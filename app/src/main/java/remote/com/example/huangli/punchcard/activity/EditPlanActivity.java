package remote.com.example.huangli.punchcard.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.ctviews.ListViewForScrollView;
import remote.com.example.huangli.punchcard.model.Plan;
import remote.com.example.huangli.punchcard.model.Task;
import remote.com.example.huangli.punchcard.model.User;
import remote.com.example.huangli.punchcard.utils.ToastUtils;

/**
 * Created by huangli on 16/6/23.
 */
public class EditPlanActivity extends BaseActivity implements View.OnClickListener{
    private ListViewForScrollView listviewTasks;
    private ItemListViewTaskAdapter itemListViewTaskAdapter;

    public static final int REQUEST_CODE_ADD_TASK = 0x01;
    public static final int REQUEST_CODE_MODIFY_TASK = 0x02;

    public static final String BUNDLE_KEY_DESCRIBE = "BUNDLE_KEY_DESCRIBE";
    public static final String BUNDLE_KEY_DAYS = "BUNDLE_KEY_DAYS";

    private Plan plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);
        Intent intent = getIntent();
        if (intent != null){
            int type = intent.getIntExtra("type",Plan.TYPE_100_DAYS);
            plan = new Plan(type);
        }else {
            finish();
        }
        findViews();
        setListeners();
        initUi();
    }

    private void initUi() {
        itemListViewTaskAdapter = new ItemListViewTaskAdapter(this,plan.getTasks());
        listviewTasks.setAdapter(itemListViewTaskAdapter);
        listviewTasks.setDivider(null);
        listviewTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(EditPlanActivity.this,AddTaskActivity.class);
                intent.putExtra(BUNDLE_KEY_DESCRIBE,plan.getTasks().get(position).getDescribe());
                intent.putExtra(BUNDLE_KEY_DAYS,plan.getTasks().get(position).getRemindDays());
                startActivityForResult(intent, REQUEST_CODE_MODIFY_TASK);
            }
        });
    }

    private void setListeners() {
    }

    private void findViews(){
        listviewTasks = (ListViewForScrollView) findViewById(R.id.listview_tasks);
        findViewById(R.id.btn_addtask).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
    }

    private EditText getEdGoal(){
        return (EditText) findViewById(R.id.ed_goal);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addtask:
                //TODO implement
                startActivityForResult(new Intent(this,AddTaskActivity.class), REQUEST_CODE_ADD_TASK);
                break;
            case R.id.btn_ok:
                //TODO implement
                if (isFormatOk()){
                    plan.setDescribe(getEdGoal().getText().toString());
                    User.getInstance().addPlan(plan);
                    finish();
                }
                break;
        }
    }

    private boolean isFormatOk(){
        if (getEdGoal().getText().toString().equals("")){
            ToastUtils.showShortToast(this,R.string.toast_format_error_edit_plan);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_ADD_TASK){
            String describe = data.getExtras().getString(BUNDLE_KEY_DESCRIBE);
            int[] days = data.getExtras().getIntArray(BUNDLE_KEY_DAYS);
            Task task = new Task(describe,days,false);
            plan.addTask(task);
            itemListViewTaskAdapter.notifyDataSetChanged();
        }
    }

    class ItemListViewTaskAdapter extends BaseAdapter {

        private List<Task> objects;

        private Context context;
        private LayoutInflater layoutInflater;

        public ItemListViewTaskAdapter(Context context,List<Task> objects) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
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
                convertView = layoutInflater.inflate(R.layout.item_list_view_task, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((Task)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(Task object, ViewHolder holder) {
            //TODO implement
            StringBuilder sb = new StringBuilder();
            sb.append(getString(R.string.remind_tips)+" ");
            if (object.isRemindEveryDay()){
                sb.append(getString(R.string.remind_every_day));
            }else {
                if (object.getRemindDays()[0] == 1){
                    sb.append(getString(R.string.day1)+" ");
                }
                if (object.getRemindDays()[1] == 1){
                    sb.append(getString(R.string.day2)+" ");
                }
                if (object.getRemindDays()[2] == 1){
                    sb.append(getString(R.string.day3)+" ");
                }
                if (object.getRemindDays()[3] == 1){
                    sb.append(getString(R.string.day4)+" ");
                }
                if (object.getRemindDays()[4] == 1){
                    sb.append(getString(R.string.day5)+" ");
                }
                if (object.getRemindDays()[5] == 1){
                    sb.append(getString(R.string.day6)+" ");
                }
                if (object.getRemindDays()[6] == 1){
                    sb.append(getString(R.string.day7)+" ");
                }
            }
            holder.tvReminddays.setText(sb.toString());
            holder.tvDescribe.setText(object.getDescribe());
        }

        protected class ViewHolder {
            private LinearLayout layout1;
            private TextView tvDescribe;
            private TextView tvReminddays;
            private RelativeLayout layoutModifyBtn;
            private TextView tvEdit;

            public ViewHolder(View view) {
                layout1 = (LinearLayout) view.findViewById(R.id.layout1);
                tvDescribe = (TextView) view.findViewById(R.id.tv_describe);
                tvReminddays = (TextView) view.findViewById(R.id.tv_reminddays);
                layoutModifyBtn = (RelativeLayout) view.findViewById(R.id.layout_modify_btn);
                tvEdit = (TextView) view.findViewById(R.id.tv_edit);
            }
        }
    }
}
