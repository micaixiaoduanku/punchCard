package remote.com.example.huangli.punchcard.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.model.PicsShare;
import remote.com.example.huangli.punchcard.utils.PCLog;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by huangli on 16/4/3.
 */
public class SharePicsPagerAdpter extends PagerAdapter {
    private final String TAG = "SharePicsPagerAdpter";
    public interface OnPageSizeChangeListener{
        void pageSizeChange(int num);
    }

    private OnPageSizeChangeListener onPageSizeChangeListener;

    private Context mContext;
//    private ProgressBar progressBar;
    private View[] views;
    private PhotoViewAttacher photoViewAttacher;


    private List<PicsShare> picsShares = new ArrayList<>();

    public SharePicsPagerAdpter(Context context, List<PicsShare> picsShares){
        mContext = context;
        views = new View[picsShares.size()];
        this.picsShares.clear();
        for (PicsShare item : picsShares){
            this.picsShares.add(item);
        }
    }

    public void setOnPageSizeChangeListener(OnPageSizeChangeListener onPageSizeChangeListener){
        this.onPageSizeChangeListener = onPageSizeChangeListener;
    }

    public void deletePic(int position){
        picsShares.remove(position);
        notifyDataSetChanged();
    }


    // 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
    @Override
    public int getCount(){
        return picsShares.size();
    }

    // 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return  arg0 == arg1;
    }

    // PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView(views[position]);
    }

    /**
     * 跳转到每个页面都要执行的方法
     */
        /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#setPrimaryItem(android.view.View, int, java.lang.Object)
         */
    @Override
    public void setPrimaryItem(View container,final int position, Object object) {
        PCLog.i(TAG+" setPrimaryItem "+position);
        View view = (View)object;
        if (view == null){
            return;
        }
        ImageView imageView = (ImageView)view.findViewById(R.id.image_sample);
        photoViewAttacher = new PhotoViewAttacher(imageView);
        photoViewAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
        PicsShare picsShare = picsShares.get(position);
        imageView.setImageBitmap(picsShare.getBitmap());
    }

    // 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        PCLog.i(TAG+" instantiateItem "+position);
        View singleView = LayoutInflater.from(mContext).inflate(R.layout.layout_pic_share, null);
//        progressBar = (ProgressBar)singleView.findViewById(R.id.progress_bar);
        final ImageView imageView = (ImageView)singleView.findViewById(R.id.image_sample);
        views[position] = singleView;
        view.addView(singleView);
        return singleView;
    }

    @Override
    public int getItemPosition(Object object)   {
        return POSITION_NONE;
    }
}
