package remote.com.example.huangli.punchcard.model;

import android.graphics.Bitmap;

import java.io.Serializable;

import remote.com.example.huangli.punchcard.utils.BitmapUtils;


/**
 * Created by huangli on 16/4/3.
 */
public class PicsShare implements Serializable {

    private static final long serialVersionUID = -7060210544600464481L;

    public boolean isaddbtn = false;
    private byte[] bitmapbytes;


    public PicsShare(Bitmap bitmap, boolean isaddbtn){
        this.isaddbtn = isaddbtn;
        setBitmap(bitmap);
    }

    public void setBitmap(Bitmap bitmap){
        bitmapbytes = BitmapUtils.getBytes(bitmap);
    }

    public Bitmap getBitmap(){
       return BitmapUtils.getBitmap(bitmapbytes);
    }



}
