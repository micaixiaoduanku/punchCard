package remote.com.example.huangli.punchcard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.pojo.Pojo_Plan;

/**
 * Created by huangli on 16/6/23.
 */
public class NewPlanActivity extends BaseActivity implements View.OnClickListener{
    private RelativeLayout layoutBtnWeek;
    private RelativeLayout layoutBtnMonth;
    private RelativeLayout layoutBtn100days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_plan);

        layoutBtnWeek = (RelativeLayout) findViewById(R.id.layout_btn_week);
        layoutBtnMonth = (RelativeLayout) findViewById(R.id.layout_btn_month);
        layoutBtn100days = (RelativeLayout) findViewById(R.id.layout_btn_100days);
        layoutBtnWeek.setOnClickListener(this);
        layoutBtnMonth.setOnClickListener(this);
        layoutBtn100days.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_btn_week:
                Intent intent1 = new Intent(this,EditPlanActivity.class);
                intent1.putExtra("type", Pojo_Plan.TYPE_WEEK);
                startActivity(intent1);
                finish();
                break;
            case R.id.layout_btn_month:
                Intent intent2 = new Intent(this,EditPlanActivity.class);
                intent2.putExtra("type", Pojo_Plan.TYPE_MONTH);
                startActivity(intent2);
                finish();
                break;
            case R.id.layout_btn_100days:
                Intent intent3 = new Intent(this,EditPlanActivity.class);
                intent3.putExtra("type", Pojo_Plan.TYPE_100_DAYS);
                startActivity(intent3);
                finish();
                break;
        }
    }
}
