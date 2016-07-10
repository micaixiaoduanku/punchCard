package remote.com.example.huangli.punchcard.pojo;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by huangli on 16/6/30.
 */
public class Pojo_Task implements Serializable{
    public String describe;
    public boolean isComplated;
    public Integer[] remindDays;

    public Pojo_Task( String describe, boolean isComplated, Integer[] remindDays) {
        this.describe = describe;
        this.isComplated = isComplated;
        this.remindDays = remindDays;
    }

    @Override
    public String toString() {
        return "Pojo_Task{" +
                "describe='" + describe + '\'' +
                ", isComplated=" + isComplated +
                ", remindDays=" + Arrays.toString(remindDays) +
                '}';
    }
}
