package remote.com.example.huangli.punchcard.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import remote.com.example.huangli.punchcard.MainApp;
import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.control.UserControler;
import remote.com.example.huangli.punchcard.http.HttpProtocol;
import remote.com.example.huangli.punchcard.http.Network;
import remote.com.example.huangli.punchcard.pojo.Pojo_Result;
import remote.com.example.huangli.punchcard.pojo.Pojo_User;
import remote.com.example.huangli.punchcard.utils.ToastUtils;

/**
 * Created by huangli on 16/6/30.
 */
public class LogInActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout layoutLogin;
    private LinearLayout layoutBtnGo;
    private LinearLayout layoutSignIn;
    private TextView btnGo;
    private Button btnSignIn,btnLogin;

    private int STATE_PAGE_LAYOUT_LOGIN = 0;
    private int STATE_PAGE_LAYOUT_SIGN = 1;

    private int pageState = STATE_PAGE_LAYOUT_LOGIN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutLogin = (LinearLayout) findViewById(R.id.layout_login);
        layoutBtnGo = (LinearLayout) findViewById(R.id.layout_btn_go);
        layoutSignIn = (LinearLayout) findViewById(R.id.layout_sign_in);
        btnSignIn = (Button)findViewById(R.id.btn_sign_in);
        btnLogin = (Button)findViewById(R.id.btn_login);
        layoutLogin.setOnClickListener(this);
        layoutBtnGo.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnGo = (TextView) findViewById(R.id.btn_go);
        initUi();

    }

    private void initUi() {
        getEditLoginAcount().setText(UserControler.getAccountFromPref());
        getEditLoginPassword().setText(UserControler.getPasswordFromPref());
    }

    private EditText getEditLoginAcount(){
        return (EditText) findViewById(R.id.edit_acount_login);
    }

    private EditText getEditLoginPassword(){
        return (EditText) findViewById(R.id.edit_password_login);
    }

    private EditText getEditSignInNickName(){
        return (EditText) findViewById(R.id.edit_sign_nickname);
    }

    private EditText getEditSignInAcount(){
        return (EditText) findViewById(R.id.edit_sign_acount);
    }

    private EditText getEditSignInPassword(){
        return (EditText) findViewById(R.id.edit_sign_password);
    }

    private void skipToMainActivity(Pojo_User o){
        if (o != null){
            MainApp.user = o;
            UserControler.putAccountToPref(o.account);
            UserControler.putPasswordToPref(o.passoword);
            startActivity(new Intent(LogInActivity.this,MainActivity.class));
            finish();
        }
    }

    private void login(String account,String password){
        HashMap<String,Object> map = new HashMap<>();
        map.put("account",account);
        map.put("password",password);
        Network.get(this).asyncPost(HttpProtocol.URLS.USER_LOGIN, map, new Network.JsonCallBack<Pojo_User>() {
            @Override
            public void onSuccess(Pojo_User o) {
                if (o.code == 0){
                    ToastUtils.showShortToast(LogInActivity.this,R.string.login_activity_login_succeed);
                    skipToMainActivity(o);
                }else {
                    ToastUtils.showShortToast(LogInActivity.this,R.string.login_activity_login_failed);
                }
            }

            @Override
            public void onFailed(int code, String message, Exception e) {
                ToastUtils.showShortToast(LogInActivity.this,R.string.login_activity_login_failed);
            }

            @Override
            public Class<Pojo_User> getObjectClass() {
                return Pojo_User.class;
            }
        });
    }

    private void signIn(String account,String password,String nickName){
        HashMap<String,Object> map = new HashMap<>();
        map.put("account",account);
        map.put("password",password);
        map.put("nickname",nickName);
        Network.get(this).asyncPost(HttpProtocol.URLS.USER_SIGNIN, map, new Network.JsonCallBack<Pojo_User>() {
            @Override
            public void onSuccess(Pojo_User o) {
                if (o.code == 0){
                    skipToMainActivity(o);
                    ToastUtils.showShortToast(LogInActivity.this,R.string.login_activity_signin_succeed);
                }else {
                    ToastUtils.showShortToast(LogInActivity.this,R.string.login_activity_signin_failed);
                }
            }

            @Override
            public void onFailed(int code, String message, Exception e) {
                ToastUtils.showShortToast(LogInActivity.this,R.string.login_activity_signin_failed);
            }

            @Override
            public Class<Pojo_User> getObjectClass() {
                return Pojo_User.class;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sign_in:
                signIn(getEditSignInAcount().getText().toString(),getEditSignInPassword().getText().toString(),getEditSignInNickName().getText().toString());
                break;
            case R.id.btn_login:
                login(getEditLoginAcount().getText().toString(),getEditLoginPassword().getText().toString());
                break;
            case R.id.layout_btn_go:
                if (pageState == STATE_PAGE_LAYOUT_LOGIN){
                    pageState = STATE_PAGE_LAYOUT_SIGN;
                    btnGo.setText(R.string.login_activity_go_login);
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(layoutLogin, "rotationY", 0.0F, 90.0F);
                    final ObjectAnimator animator2 = ObjectAnimator.ofFloat(layoutSignIn, "rotationY", -90.0F, 0.0F);
                    animator1.setDuration(500);
                    animator2.setDuration(500);
                    animator1.start();
                    animator1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            layoutSignIn.setVisibility(View.VISIBLE);
                            layoutLogin.setVisibility(View.GONE);
                            animator2.start();
                        }
                    });
                }else if (pageState == STATE_PAGE_LAYOUT_SIGN){
                    pageState = STATE_PAGE_LAYOUT_LOGIN;
                    btnGo.setText(R.string.login_activity_go_sign);
                    ObjectAnimator animator1 = ObjectAnimator.ofFloat(layoutSignIn, "rotationY", 0.0F, 90.0F);
                    final ObjectAnimator animator2 = ObjectAnimator.ofFloat(layoutLogin, "rotationY", -90.0F, 0.0F);
                    animator1.setDuration(500);
                    animator2.setDuration(500);
                    animator1.start();
                    animator1.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            layoutLogin.setVisibility(View.VISIBLE);
                            layoutSignIn.setVisibility(View.GONE);
                            animator2.start();
                        }
                    });
                }
                break;
        }
    }
}
