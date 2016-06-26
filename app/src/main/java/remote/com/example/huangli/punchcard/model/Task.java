package remote.com.example.huangli.punchcard.model;

/**
 * Created by huangli on 16/6/20.
 */
public class Task {
    private String describe;
    private boolean isComplated;
    private int[] remindDays;

    public Task(String describe,int[] remindDays,boolean isComplated){
        this.describe = describe;
        this.isComplated = isComplated;
        this.remindDays = remindDays;
    }

    public int[] getRemindDays() {
        return remindDays;
    }

    public void setRemindDays(int[] remindDays) {
        this.remindDays = remindDays;
    }

    public boolean isRemindEveryDay(){
        for (int i : remindDays){
            if (i == 0){
                return false;
            }
        }
        return true;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public boolean isComplated() {
        return isComplated;
    }

    public void setComplated(boolean complated) {
        isComplated = complated;
    }
}
