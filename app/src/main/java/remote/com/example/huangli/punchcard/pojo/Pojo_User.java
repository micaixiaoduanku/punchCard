package remote.com.example.huangli.punchcard.pojo;

import java.io.Serializable;

/**
 * Created by huangli on 16/6/30.
 */
public class Pojo_User extends Pojo_Result implements Serializable {
    public String account;
    public String passoword;
    public String nickname;
    public String signature;
    public int lv;
    public int curProgress;

    public Pojo_User(int code, String msg, String account, int lv, String nickname, String signature, String passoword, int curProgress) {
        super(code, msg);
        this.account = account;
        this.lv = lv;
        this.nickname = nickname;
        this.signature = signature;
        this.passoword = passoword;
        this.curProgress = curProgress;
    }

    @Override
    public String toString() {
        return "Pojo_User{" +
                "account='" + account + '\'' +
                ", passoword='" + passoword + '\'' +
                ", nickname='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", lv=" + lv +
                ", curProgress=" + curProgress +
                '}';
    }
}
