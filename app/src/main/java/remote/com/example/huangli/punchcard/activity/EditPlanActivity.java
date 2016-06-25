package remote.com.example.huangli.punchcard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.ctviews.ListViewForScrollView;

/**
 * Created by huangli on 16/6/23.
 */
public class EditPlanActivity extends BaseActivity implements View.OnClickListener{
    private ListViewForScrollView listviewTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);

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
                startActivity(new Intent(this,AddTaskActivity.class));
                break;
            case R.id.btn_ok:
                //TODO implement
                break;
        }
    }
}
