package remote.com.example.huangli.punchcard.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangli on 16/7/1.
 */
public class Pojo_Plan_List extends Pojo_Result implements Serializable {

    public List<Pojo_Plan> list;

    public Pojo_Plan_List(int code, String msg) {
        super(code, msg);
    }
}
