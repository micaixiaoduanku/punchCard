package remote.com.example.huangli.punchcard.adpter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.model.PicsShare;
import remote.com.example.huangli.punchcard.utils.BitmapUtils;
import remote.com.example.huangli.punchcard.utils.DisplayUtils;
import remote.com.example.huangli.punchcard.utils.PCLog;

/**
 * Created by huangli on 16/6/26.
 */
public class SharePicGridAdpter extends BaseAdapter {

    public final String TAG = "SharePrizePicGridAdpter";

    private Context mContext;
    public final int MAX_PICS_NUM = 4;
    private ArrayList<PicsShare> piclist = new ArrayList<>();
    private boolean addbtnexist = false;
    private class ViewHolder{
        public ImageView shareImage;
    }

    public ArrayList<PicsShare> getContentPicsShares(){
        ArrayList<PicsShare> picsShares = new ArrayList<>();
        if (addbtnexist){
            for (int i = 0 ; i <piclist.size()-1;i++){
                picsShares.add(piclist.get(i));
            }
            return picsShares;
        }else {
            for (int i = 0 ; i <piclist.size();i++){
                picsShares.add(piclist.get(i));
            }
            return picsShares;
        }
    }

    public boolean isaddBtn(int position){
        return piclist.get(position).isaddbtn;
    }

    public ArrayList<PicsShare> getAllPiclist(){
        return piclist;
    }

    public SharePicGridAdpter(Context context){
        mContext = context;
        if (!addbtnexist){
            Bitmap addicon = BitmapUtils.drawableToBitmap(mContext.getResources().getDrawable(R.mipmap.btn_share_add));
            piclist.add(new PicsShare(addicon, true));
            addbtnexist = true;
        }
    }

    public int getPicsSize(){
        return piclist.size();
    }

    public void addPic(PicsShare pic){
        if (piclist.size() == MAX_PICS_NUM && addbtnexist){
            piclist.remove(piclist.size()-1);
            addbtnexist = false;
        }
        piclist.add(0,pic);
    }

    public void deletePics(ArrayList<Integer> picpositions){
        for (int i : picpositions){
            piclist.remove(i);
        }
        if (piclist.size() < MAX_PICS_NUM && !addbtnexist){
            Bitmap addicon = BitmapUtils.drawableToBitmap(mContext.getResources().getDrawable(R.mipmap.btn_share_add));
            piclist.add(new PicsShare(addicon, true));
            addbtnexist = true;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return piclist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_share_pic, null);
            holder = new ViewHolder();
            holder.shareImage = (ImageView)convertView.findViewById(R.id.iv_share);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        int scaledWidth = DisplayUtils.getScreenWidth(mContext)/4;
        int scaledheight = DisplayUtils.getScreenWidth(mContext) /4;
        if (piclist.get(position).getBitmap() != null){
            PCLog.i(TAG+"getView() bitmap != null");
//            Bitmap bitmap = Bitmap.createScaledBitmap(piclist.get(position).bitmap, scaledWidth, scaledheight, true);
            if (piclist.get(position).isaddbtn){
                PCLog.i(TAG+"getView() 这是一个添加按钮,直接set到imageview position = "+position);
                holder.shareImage.setImageBitmap(piclist.get(position).getBitmap());
            }else{
                PCLog.i(TAG+"getView() 这不是添加按钮,需要去取中心内容 position = "+position);
                Bitmap bitmap  = BitmapUtils.centerSquareScaleBitmap(piclist.get(position).getBitmap(),scaledWidth);
                holder.shareImage.setImageBitmap(bitmap != null?bitmap:piclist.get(position).getBitmap());
            }
        }
        return convertView;
    }

}