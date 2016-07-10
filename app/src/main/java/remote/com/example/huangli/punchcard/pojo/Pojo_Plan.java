package remote.com.example.huangli.punchcard.pojo;

import java.io.Serializable;

/**
 * Created by huangli on 16/6/30.
 */
public class Pojo_Plan implements Serializable{
    public final static int TYPE_100_DAYS = 0;
    public final static int TYPE_MONTH = 1;
    public final static int TYPE_WEEK = 2;
    public int type;
    public String describe;
    public String num;

    public Pojo_Plan(int type, String describe, String num) {
        this.type = type;
        this.describe = describe;
        this.num = num;
    }

    @Override
    public String toString() {
        return "Pojo_Plan{" +
                "type=" + type +
                ", describe='" + describe + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
