package remote.com.example.huangli.punchcard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangli on 16/6/25.
 */
public class User {
    private String account;
    private String nickname;
    private String signature;
    private int lv;
    private int curProgress;
    private int totalProgress;
    private List<Plan> plens = new ArrayList<>();
    private static User user;
    private int curPlanIndex = 0;

    public static User getInstance(){
        if (user == null){
            user = new User();
        }
        return user;
    }

    private User(){

    }

    public Plan getCurPlan(){
        if (curPlanIndex < plens.size()){
            return plens.get(curPlanIndex);
        }else {
            return null;
        }
    }

    public int getCurPlanIndex() {
        return curPlanIndex;
    }

    public void setCurPlanIndex(int curPlan) {
        this.curPlanIndex = curPlan;
    }

    public List<Plan> getPlens(){
        return plens;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getLv() {
        return lv;
    }

    public void setLv(int lv) {
        this.lv = lv;
    }

    public int getCurProgress() {
        return curProgress;
    }

    public void setCurProgress(int curProgress) {
        this.curProgress = curProgress;
    }

    public int getTotalProgress() {
        return totalProgress;
    }

    public void setTotalProgress(int totalProgress) {
        this.totalProgress = totalProgress;
    }

    public void addPlan(Plan plan){
        plens.add(0,plan);
    }
}
