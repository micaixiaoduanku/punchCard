package remote.com.example.huangli.punchcard.model;

/**
 * Created by huangli on 16/6/20.
 */
public class Task {
    public String describe;
    public boolean isComplated;
    public Task(String describe,boolean isComplated){
        this.describe = describe;
        this.isComplated = isComplated;
    }
}
