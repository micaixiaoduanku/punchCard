package remote.com.example.huangli.punchcard.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.ctviews.ItemLayoutCbDay;

/**
 * Created by huangli on 16/6/25.
 */
public class AddTaskActivity extends BaseActivity implements View.OnClickListener{
    private ItemLayoutCbDay cbLayout1;
    private ItemLayoutCbDay cbLayout2;
    private ItemLayoutCbDay cbLayout3;
    private ItemLayoutCbDay cbLayout4;
    private ItemLayoutCbDay cbLayout5;
    private ItemLayoutCbDay cbLayout6;
    private ItemLayoutCbDay cbLayout7;
    private ItemLayoutCbDay cbLayout8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        initUi();
    }

    private void initUi(){
        cbLayout1 = (ItemLayoutCbDay) findViewById(R.id.cb_layout1);
        cbLayout2 = (ItemLayoutCbDay) findViewById(R.id.cb_layout2);
        cbLayout3 = (ItemLayoutCbDay) findViewById(R.id.cb_layout3);
        cbLayout4 = (ItemLayoutCbDay) findViewById(R.id.cb_layout4);
        cbLayout5 = (ItemLayoutCbDay) findViewById(R.id.cb_layout5);
        cbLayout6 = (ItemLayoutCbDay) findViewById(R.id.cb_layout6);
        cbLayout7 = (ItemLayoutCbDay) findViewById(R.id.cb_layout7);
        cbLayout8 = (ItemLayoutCbDay) findViewById(R.id.cb_layout8);
        cbLayout1.setTitle(R.string.day1);
        cbLayout2.setTitle(R.string.day2);
        cbLayout3.setTitle(R.string.day3);
        cbLayout4.setTitle(R.string.day4);
        cbLayout5.setTitle(R.string.day5);
        cbLayout6.setTitle(R.string.day6);
        cbLayout7.setTitle(R.string.day7);
        cbLayout8.setTitle(getString(R.string.day_all)+"    ");
        findViewById(R.id.btn_cancel).setOnClickListener(this);
        findViewById(R.id.btn_ok).setOnClickListener(this);
        cbLayout8.setOnCheckChangeListener(new ItemLayoutCbDay.OnCheckChangeListener() {
            @Override
            public void checkChangeListener(boolean isChecked) {
                cbLayout1.setChecked(true);
                cbLayout2.setChecked(true);
                cbLayout3.setChecked(true);
                cbLayout4.setChecked(true);
                cbLayout5.setChecked(true);
                cbLayout6.setChecked(true);
                cbLayout7.setChecked(true);
            }
        });
    }

    private EditText getEditTask(){
        return (EditText) findViewById(R.id.edit_task);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                //TODO implement
                finish();
                break;
            case R.id.btn_ok:
                //TODO implement
                break;
        }
    }
}
