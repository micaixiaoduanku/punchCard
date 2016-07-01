package remote.com.example.huangli.punchcard.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.adpter.SharePicsPagerAdpter;
import remote.com.example.huangli.punchcard.control.PicControler;
import remote.com.example.huangli.punchcard.fragment.TaskFragment;
import remote.com.example.huangli.punchcard.model.PicsShare;
import remote.com.example.huangli.punchcard.utils.PCLog;


/**
 * Created by huangli on 16/4/3.
 */
public class SharePicsPagerActivity extends Activity{

    public final String TAG = "SharePicsPagerActivity";

    private ViewPager viewPager;
    private ImageView IvDelete;
    private SharePicsPagerAdpter sharePicsPagerAdpter;
    private ArrayList<Integer> deletepicpostions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_pics);
        IvDelete = (ImageView)findViewById(R.id.iv_delete);
        IvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                deletepicpostions.add(position);
                PCLog.i(TAG+"点击删除按钮 position "+position);
                sharePicsPagerAdpter.deletePic(position);
                if (sharePicsPagerAdpter.getCount() == 0){
                    onBackPressed();
                }
            }
        });
        Intent intent = getIntent();
//        ArrayList<PicsShare> list = (ArrayList<PicsShare>) intent.getSerializableExtra("picsshares");
        if (TaskFragment.sharePicGridAdpter != null){
            ArrayList<PicsShare> list = TaskFragment.sharePicGridAdpter.getContentPicsShares();
            if (list != null){
                int position = intent.getIntExtra("position",0);
                viewPager = (ViewPager) findViewById(R.id.viewpager_pic);
                sharePicsPagerAdpter = new SharePicsPagerAdpter(this,list);
                viewPager.setAdapter(sharePicsPagerAdpter);
                viewPager.setCurrentItem(position);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        for (int i : deletepicpostions){
            PCLog.i(TAG+"删除图片 position "+i);
        }
        intent.putIntegerArrayListExtra("picpositions",deletepicpostions);
        setResult(PicControler.RESULT_DELETE_PICS,intent);
        finish();
        super.onBackPressed();
    }

}
