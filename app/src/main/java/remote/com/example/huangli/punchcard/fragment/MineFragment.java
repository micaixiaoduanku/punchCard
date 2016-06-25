package remote.com.example.huangli.punchcard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.activity.PlanActivity;
import remote.com.example.huangli.punchcard.model.User;

/**
 * Created by huangli on 16/6/19.
 */
public class MineFragment extends Fragment implements View.OnClickListener{

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    private ImageView portrait;
    private LinearLayout layoutInfomation;
    private TextView tvNickName;
    private TextView tvNickLv;
    private TextView tvProgress;
    private TextView tvSignature;
    private RelativeLayout layoutPlan;
    private RelativeLayout layoutSetting;
    private RelativeLayout layoutEdit;
    private RelativeLayout layoutExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        initUi();
    }

    private void initUi() {
        tvNickName.setText(User.getInstance().getNickname());
        tvNickLv.setText(String.valueOf(User.getInstance().getLv()));
        tvProgress.setText(String.valueOf(User.getInstance().getCurProgress()));
        tvSignature.setText(User.getInstance().getSignature());
    }

    private void findViews(View view) {
        portrait = (ImageView) view.findViewById(R.id.portrait);
        layoutInfomation = (LinearLayout) view.findViewById(R.id.layout_infomation);
        tvNickName = (TextView) view.findViewById(R.id.tv_nick_name);
        tvNickLv = (TextView) view.findViewById(R.id.tv_nick_lv);
        tvProgress = (TextView) view.findViewById(R.id.tv_progress);
        tvSignature = (TextView) view.findViewById(R.id.tv_signature);
        layoutPlan = (RelativeLayout) view.findViewById(R.id.layout_plan);
        layoutSetting = (RelativeLayout) view.findViewById(R.id.layout_setting);
        layoutEdit = (RelativeLayout) view.findViewById(R.id.layout_edit);
        layoutExit = (RelativeLayout) view.findViewById(R.id.layout_exit);
        layoutPlan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_plan:
                startActivity(new Intent(getActivity(), PlanActivity.class));
                break;
        }
    }
}
