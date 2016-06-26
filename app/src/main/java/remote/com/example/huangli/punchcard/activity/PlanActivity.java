package remote.com.example.huangli.punchcard.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.model.Plan;
import remote.com.example.huangli.punchcard.model.Task;
import remote.com.example.huangli.punchcard.model.User;

/**
 * Created by huangli on 16/6/20.
 */
public class PlanActivity extends BaseActivity implements View.OnClickListener{
    private Button newPlanBtn;
    private ListView listView;
    private ItemListViewPlanAdapter itemListViewPlanAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        findViews();
        setListeners();
        initUi();
    }

    private void initUi() {
        itemListViewPlanAdapter = new ItemListViewPlanAdapter(this);
        itemListViewPlanAdapter.setPlans(User.getInstance().getPlens(),User.getInstance().getCurPlanIndex());
        listView.setAdapter(itemListViewPlanAdapter);
    }

    private void setListeners(){
        newPlanBtn.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User.getInstance().setCurPlanIndex(position);
                itemListViewPlanAdapter.setPlanIndex(position);
                itemListViewPlanAdapter.notifyDataSetChanged();
            }
        });
    }

    private void findViews(){
        newPlanBtn = (Button)findViewById(R.id.btn_new_plan);
        listView = (ListView)findViewById(R.id.listview);
        listView.setDivider(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemListViewPlanAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_new_plan:
                startActivity(new Intent(this,NewPlanActivity.class));
                break;
        }
    }

    class ItemListViewPlanAdapter extends BaseAdapter {

        private List<Plan> objects = new ArrayList<>();

        private Context context;
        private LayoutInflater layoutInflater;
        private int curPlanIndex;

        public void setPlans(List<Plan> objects,int curPlanIndex){
            this.objects = objects;
            this.curPlanIndex = curPlanIndex;
        }

        public void setPlanIndex(int index){
            curPlanIndex = index;
        }

        public ItemListViewPlanAdapter(Context context) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Plan getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_list_view_plan, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews(position,(Plan)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(final int position, final Plan object, ViewHolder holder) {
            //TODO implement
            List<Task> tasks = object.getTasks();
            int type = object.getType();
            String describe = object.getDescribe();
            switch (type){
                case Plan.TYPE_100_DAYS:
                    holder.tvType.setText(R.string.type_plan_100days);
                    break;
                case Plan.TYPE_MONTH:
                    holder.tvType.setText(R.string.type_plan_month);
                    break;
                case Plan.TYPE_WEEK:
                    holder.tvType.setText(R.string.type_plan_week);
                    break;
            }
            holder.tvDescribe.setText(describe);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < tasks.size(); i++){
                sb.append(getResources().getString(R.string.task_tips)+(i+1)+":"+tasks.get(i).getDescribe()+"\n");
            }
            holder.tvTasksCentent.setText(sb.toString());
            if (position == curPlanIndex){
                holder.checkBox.setChecked(true);
            }else {
                holder.checkBox.setChecked(false);
            }
        }

        protected class ViewHolder {
            private TextView tvType;
            private TextView tvDescribe;
            private TextView tvTasksCentent;
            private CheckBox checkBox;

            public ViewHolder(View view) {
                tvType = (TextView) view.findViewById(R.id.tv_type);
                tvDescribe = (TextView) view.findViewById(R.id.tv_describe);
                tvTasksCentent = (TextView)view.findViewById(R.id.tv_tasks_centent);
                checkBox = (CheckBox)view.findViewById(R.id.checkbox);
                checkBox.setClickable(false);
                checkBox.setFocusable(false);
            }
        }
    }
}
