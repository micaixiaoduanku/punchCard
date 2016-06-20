package remote.com.example.huangli.punchcard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import remote.com.example.huangli.punchcard.R;

/**
 * Created by huangli on 16/6/19.
 */
public class TaskFragment extends Fragment{
    private ListView listView;
    private ItemListviewTaskAdapter itemListviewTaskAdapter;
    public static TaskFragment newInstance() {
        TaskFragment fragment = new TaskFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);
        findviews(view);
        initUi();
        return view;
    }

    private void initUi() {
        itemListviewTaskAdapter = new ItemListviewTaskAdapter(getActivity());
        listView.setAdapter(itemListviewTaskAdapter);
        List<Task> objects = new ArrayList<>();
        objects.add(new Task("记忆30个单词",false));
        objects.add(new Task("进行有氧训练一个小时",false));
        objects.add(new Task("进行打卡app开发",false));
        itemListviewTaskAdapter.setData(objects);
    }

    private void findviews(View view){
        listView = (ListView)view.findViewById(R.id.listview_tasks);
    }

    private class Task{
        public String describe;
        public boolean isComplated;
        public Task(String describe,boolean isComplated){
            this.describe = describe;
            this.isComplated = isComplated;
        }
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
            initializeViews((Task)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(Task object, ViewHolder holder) {
            //TODO implement
            holder.tvTask.setText(object.describe);
            holder.checkbox.setChecked(object.isComplated);
        }

        protected class ViewHolder {
            private TextView tvTask;
            private CheckBox checkbox;

            public ViewHolder(View view) {
                tvTask = (TextView) view.findViewById(R.id.tv_task);
                checkbox = (CheckBox) view.findViewById(R.id.checkbox);
            }
        }
    }
}
