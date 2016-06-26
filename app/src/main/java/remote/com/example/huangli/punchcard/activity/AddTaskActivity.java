package remote.com.example.huangli.punchcard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.ctviews.ItemLayoutCbDay;
import remote.com.example.huangli.punchcard.utils.ToastUtils;

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
        praseIntent(getIntent());
    }

    private void praseIntent(Intent intent){
        if (intent == null){
            return;
        }
        String describe = intent.getStringExtra(EditPlanActivity.BUNDLE_KEY_DESCRIBE);
        int[] days = intent.getIntArrayExtra(EditPlanActivity.BUNDLE_KEY_DAYS);
        if (describe != null && days != null && days.length >= 7){
            getEditTask().setText(describe);
            getEditTask().setSelection(getEditTask().getText().toString().length());
            if (days[0] == 1){
                cbLayout1.setChecked(true);
            }
            if (days[1] == 1){
                cbLayout2.setChecked(true);
            }
            if (days[2] == 1){
                cbLayout3.setChecked(true);
            }
            if (days[3] == 1){
                cbLayout4.setChecked(true);
            }
            if (days[4] == 1){
                cbLayout5.setChecked(true);
            }
            if (days[5] == 1){
                cbLayout6.setChecked(true);
            }
            if (days[6] == 1){
                cbLayout7.setChecked(true);
            }
        }
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
                if (isFormatOk()){
                    setResult(RESULT_OK,getResultIntent());
                    finish();
                }
                break;
        }
    }


    private boolean isFormatOk(){
        if (!cbLayout1.isChecked() && !cbLayout2.isChecked()
                && !cbLayout3.isChecked() && !cbLayout4.isChecked()
                && !cbLayout5.isChecked() && !cbLayout6.isChecked()
                && !cbLayout7.isChecked()){
            ToastUtils.showShortToast(this,R.string.toast_format_error_days_choice);
            return false;
        }
        if (getEditTask().getText().toString().equals("")){
            ToastUtils.showShortToast(this,R.string.toast_format_error_edit_task);
            return false;
        }
        return true;
    }


    private Intent getResultIntent(){
        Intent intent = new Intent();
        intent.putExtra(EditPlanActivity.BUNDLE_KEY_DESCRIBE,getEditTask().getText().toString());
        int[] days = new int[]{cbLayout1.isChecked()?1:0,cbLayout2.isChecked()?1:0,cbLayout3.isChecked()?1:0,cbLayout4.isChecked()?1:0,cbLayout5.isChecked()?1:0,
                cbLayout6.isChecked()?1:0,cbLayout7.isChecked()?1:0};
        intent.putExtra(EditPlanActivity.BUNDLE_KEY_DAYS,days);
        return intent;
    }
}
