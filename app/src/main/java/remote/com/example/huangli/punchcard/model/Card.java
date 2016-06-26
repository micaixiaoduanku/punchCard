package remote.com.example.huangli.punchcard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangli on 16/6/26.
 */
public class Card {
    private String type;
    private List<Task> tasks = new ArrayList<>();
    private String describe;
    private String nickName;

    public Card(String nickName, String type, String describe){
        this.nickName = nickName;
        this.type = type;
        this.describe = describe;
    }

    public Card(String nickName, String type, String describe, List<Task> tasks){
        this.nickName = nickName;
        this.type = type;
        this.describe = describe;
        newTasks(tasks);
    }

    private void newTasks(List<Task> list){
        for (Task item : list){
            Task task = new Task(item.getDescribe(),item.getRemindDays(),item.isComplated());
            this.tasks.add(task);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void addTask(Task task){
        tasks.add(task);
    }
}
