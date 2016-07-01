package remote.com.example.huangli.punchcard.pojo;

import java.io.Serializable;

/**
 * Created by huangli on 16/7/1.
 */
public class Pojo_Result implements Serializable{
    public int code;
    public String msg;

    public Pojo_Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Pojo_Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
