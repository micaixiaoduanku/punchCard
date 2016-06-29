package remote.com.example.huangli.punchcard;

import android.app.Application;

import remote.com.example.huangli.punchcard.model.Plan;
import remote.com.example.huangli.punchcard.model.Task;
import remote.com.example.huangli.punchcard.model.User;

/**
 * Created by huangli on 16/6/28.
 */
public class MainApp extends Application {
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
        User.getInstance().setLv(1);
        User.getInstance().setSignature("人之所以快乐不是因为...");
    }

    private void loadTestData(){
        Plan plan1 = new Plan(Plan.TYPE_100_DAYS,"提高英文水平和锻炼腹肌");
        Task task1 = new Task("背单词30个",new int[]{1,1,1,1,1,1,1,1},false);
        Task task2 = new Task("做有氧运动30分钟",new int[]{1,1,1,1,1,1,1,1},false);
        Task task3 = new Task("饮食清淡",new int[]{1,1,1,1,1,1,1,1},false);
        plan1.addTask(task1);
        plan1.addTask(task2);
        plan1.addTask(task3);
        Plan plan2 = new Plan(Plan.TYPE_MONTH,"学习一种乐器");
        plan2.addTask(task1);
        plan2.addTask(task2);
        plan2.addTask(task3);
        Plan plan3 = new Plan(Plan.TYPE_MONTH,"看十本书,学习一门第二外语");
        plan3.addTask(task1);
        plan3.addTask(task2);
        plan3.addTask(task3);
        User.getInstance().addPlan(plan1);
        User.getInstance().addPlan(plan2);
        User.getInstance().addPlan(plan3);
    }
}