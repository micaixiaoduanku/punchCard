package remote.com.example.huangli.punchcard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import remote.com.example.huangli.punchcard.R;

/**
 * Created by huangli on 16/6/28.
 */
public class ListDialog extends Dialog implements View.OnClickListener{
    private Button btnAlbum,btnCamera,btnCancel;
    private OnBtnClickListener onBtnClickListener;

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_album:
                if (onBtnClickListener != null){
                    onBtnClickListener.btnAlbumClick();
                }
                break;
            case R.id.btn_camera:
                if (onBtnClickListener != null){
                    onBtnClickListener.btnCameraClick();
                }
                break;
            case R.id.btn_cancel:
                if (onBtnClickListener != null){
                    onBtnClickListener.btnCancelClick();
                }
                break;
        }
        cancel();
    }



    public ListDialog(Context context,OnBtnClickListener onBtnClickListener){
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_list_choice);
        findViews();
        this.onBtnClickListener = onBtnClickListener;
    }

    public interface OnBtnClickListener{
        void btnAlbumClick();
        void btnCameraClick();
        void btnCancelClick();
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

    public ListDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_list_choice);
        findViews();
    }

    private void findViews(){
        btnAlbum = (Button)findViewById(R.id.btn_album);
        btnCamera = (Button)findViewById(R.id.btn_camera);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        btnAlbum.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

}
