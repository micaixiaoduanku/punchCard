package remote.com.example.huangli.punchcard.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangli on 16/6/25.
 */
public class Plan {

    public static int TYPE_100_DAYS = 0;
    public static int TYPE_MONTH = 1;
    public static int TYPE_WEEK = 2;

    private int type;
    private String name;
    private List<Task> tasks = new ArrayList<>();

    public Plan(int type,String name){
        this.type = type;
        this.name = name;
        tasks = new ArrayList<>();
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void removeTask(){

    }
}
