package remote.com.example.huangli.punchcard.ctviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import remote.com.example.huangli.punchcard.R;

/**
 * Created by huangli on 16/6/25.
 */
public class ItemLayoutCbDay extends RelativeLayout{

    private TextView tv;
    private CheckBox cb;
    private OnCheckChangeListener onCheckChangeListener;

    public interface OnCheckChangeListener{
        void checkChangeListener(boolean isChecked);
    }

    public ItemLayoutCbDay(Context context) {
        super(context);
        init();
    }

    public ItemLayoutCbDay(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_layout_cb_day, null);
        addView(view);
        cb = (CheckBox)findViewById(R.id.checkbox);
        tv = (TextView)findViewById(R.id.tv_content);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onCheckChangeListener != null){
                    onCheckChangeListener.checkChangeListener(isChecked);
                }
            }
        });
    }

    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener){
        this.onCheckChangeListener = onCheckChangeListener;
    }

    public void setTitle(String title){
        tv.setText(title);
    }

    public void setTitle(int resid){
        tv.setText(resid);
    }

    public void setChecked(boolean checked){
        cb.setChecked(checked);
    }

    public boolean isChecked(){
        return cb.isChecked();
    }
}
