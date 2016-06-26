package remote.com.example.huangli.punchcard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangli on 16/6/25.
 */
public class Plan {

    public final static int TYPE_100_DAYS = 0;
    public final static int TYPE_MONTH = 1;
    public final static int TYPE_WEEK = 2;

    private int type;
    private String describe;
    private List<Task> tasks = new ArrayList<>();

    public Plan(int type,String describe){
        this.type = type;
        this.describe = describe;
        tasks = new ArrayList<>();
    }

    public Plan(int type){
        this.type = type;
        tasks = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<Task> getTasks(){
        return tasks;
    }

    public void addTask(Task task){
        tasks.add(0,task);
    }

    public void removeTask(){

    }
}
