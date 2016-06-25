package remote.com.example.huangli.punchcard.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.model.Plan;
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
        newPlanBtn = (Button)findViewById(R.id.btn_new_plan);
        listView = (ListView)findViewById(R.id.listview);
        itemListViewPlanAdapter = new ItemListViewPlanAdapter(this);
        itemListViewPlanAdapter.setPlans(User.getInstance().getPlens());
        listView.setAdapter(itemListViewPlanAdapter);
        newPlanBtn.setOnClickListener(this);
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

        private void setPlans(List<Plan> objects){
            this.objects = objects;
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
            initializeViews((Plan)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(Plan object, ViewHolder holder) {
            //TODO implement
        }

        protected class ViewHolder {
            private TextView tvType;
            private TextView tvDescribe;

            public ViewHolder(View view) {
                tvType = (TextView) view.findViewById(R.id.tv_type);
                tvDescribe = (TextView) view.findViewById(R.id.tv_describe);
            }
        }
    }
}
