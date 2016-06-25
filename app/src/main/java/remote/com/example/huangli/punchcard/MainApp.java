package remote.com.example.huangli.punchcard;

import android.app.Application;

import remote.com.example.huangli.punchcard.model.Plan;
import remote.com.example.huangli.punchcard.model.User;

/**
 * Created by huangli on 16/6/25.
 */
public class MainApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        login();
        loadTestData();
    }

    private void login() {
        User.getInstance().setAccount("351573105");
        User.getInstance().setNickname("阿文");
        User.getInstance().setCurProgress(0);
        User.getInstance().setTotalProgress(100);
        User.getInstance().setLv(0);
        User.getInstance().setSignature("人之所以快乐不是因为...");
    }

    private void loadTestData(){
        Plan plan1 = new Plan(Plan.TYPE_100_DAYS,"提高英文水平和锻炼腹肌");
        Plan plan2 = new Plan(Plan.TYPE_MONTH,"学习一种乐器");
        User.getInstance().addPlan(plan1);
        User.getInstance().addPlan(plan2);
    }
}
