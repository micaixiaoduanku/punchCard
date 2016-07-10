package remote.com.example.huangli.punchcard.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangli on 16/7/1.
 */
public class Pojo_Cur_Plan extends Pojo_Result implements Serializable{
    public int type;
    public String describe;
    public String num;
    public List<Pojo_Task> tasks;
    public Pojo_Cur_Plan(int code, String msg, int type, String describe, String num) {
        super(code, msg);
        this.type = type;
        this.describe = describe;
        this.num = num;
    }
}
