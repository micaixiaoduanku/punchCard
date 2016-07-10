package remote.com.example.huangli.punchcard.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangli on 16/7/10.
 */
public class Pojo_Task_List extends Pojo_Result implements Serializable {

    public List<Pojo_Task> list;

    public Pojo_Task_List(int code, String msg) {
        super(code, msg);
    }
}
