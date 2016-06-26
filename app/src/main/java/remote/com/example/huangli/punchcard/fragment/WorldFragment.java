package remote.com.example.huangli.punchcard.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.model.Card;
import remote.com.example.huangli.punchcard.model.Task;
import remote.com.example.huangli.punchcard.server.Server;

/**
 * Created by huangli on 16/6/19.
 */
public class WorldFragment extends Fragment{

    private ListView listView;
    private ItemListCardWorldAdapter itemListCardWorldAdapter;

    public static WorldFragment newInstance() {
        WorldFragment fragment = new WorldFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_world, container, false);
        findViews(view);
        initUi();
        return view;
    }

    private void initUi() {
        itemListCardWorldAdapter = new ItemListCardWorldAdapter(getActivity(), Server.worldTasks);
        listView.setAdapter(itemListCardWorldAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        itemListCardWorldAdapter.setData(Server.worldTasks);
    }

    private void findViews(View view){
        listView = (ListView)view.findViewById(R.id.listview);
    }

    class ItemListCardWorldAdapter extends BaseAdapter {

        private List<Card> objects = new ArrayList<>();

        private Context context;
        private LayoutInflater layoutInflater;

        public void setData(List<Card> objects){
            this.objects = objects;
            notifyDataSetChanged();
        }

        public ItemListCardWorldAdapter(Context context,List<Card> objects) {
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
            this.objects = objects;
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public Card getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_list_card_world, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((Card)getItem(position), (ViewHolder) convertView.getTag());
            return convertView;
        }

        private void initializeViews(Card object, ViewHolder holder) {
            //TODO implement
            holder.tvDescribe.setText(object.getDescribe());
            holder.tvNickName.setText(object.getNickName());
            StringBuilder sb = new StringBuilder();
            for (Task task:object.getTasks()){
                sb.append(getString(R.string.task_tips)+" : "+task.getDescribe()+"\n");
            }
            holder.tvTasks.setText(sb.toString());
        }

        protected class ViewHolder {
            private TextView tvNickName;
            private TextView tvDescribe;
            private TextView tvTasks;
            private TextView tvTime;

            public ViewHolder(View view) {
                tvNickName = (TextView) view.findViewById(R.id.tv_nick_name);
                tvDescribe = (TextView) view.findViewById(R.id.tv_describe);
                tvTasks = (TextView) view.findViewById(R.id.tv_tasks);
                tvTime = (TextView) view.findViewById(R.id.tv_time);
            }
        }
    }

}
