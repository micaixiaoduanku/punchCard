package remote.com.example.huangli.punchcard.control;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;

import remote.com.example.huangli.punchcard.model.PicsShare;
import remote.com.example.huangli.punchcard.utils.BitmapUtils;
import remote.com.example.huangli.punchcard.utils.PCLog;

/**
 * Created by huangli on 16/6/29.
 */
public class PicControler {

    private static String TAG = "PicControler";

    private final int COMPRESS_SIZE_WIDTH = 720;
    private final int COMPRESS_SIZE_HEIGHT = 1280;

    public static final int REQUEST_CODE_PICK_IMAGE = 0;
    public static final int REQUEST_CODE_CAPTURE_CAMEIA =1;
    public static final int RESULT_DELETE_PICS = 2;

    private Activity mActivity;
    private Uri cameiaImageUri;

    public interface PicActionListener{
        void addPic(PicsShare picsShare);
        void deletePic(ArrayList<Integer> picpositions);
    }

    private PicActionListener mPicActionListener;

    public PicControler(Activity activity,PicActionListener picActionListener){
        mActivity = activity;
        mPicActionListener = picActionListener;
    }

    private void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        mActivity.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    private void getImageFromCamera() {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, "New Picture");
            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
            cameiaImageUri = mActivity.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameiaImageUri);
            mActivity.startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
        } catch (Exception e){
            PCLog.i(e.getLocalizedMessage());
            //crash at 6.0 when Permission Denial
            // java.lang.SecurityException: Permission Denial: writing com.android.providers.media.MediaProvider uri
            // content://media/external/images/media from pid=7318, uid=10199 requires android.permission.WRITE_EXTERNAL_STORAGE, or grantUriPermission()
        }

    }

    private PicsShare receivePicFromCamera(){
        try {
            ExifInterface ei = new ExifInterface(getRealPathFromURI(cameiaImageUri));
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            Bitmap realBitamp,verticalBitmap;
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    realBitamp = BitmapUtils.decodeFromFile(new File(getRealPathFromURI(cameiaImageUri)),COMPRESS_SIZE_HEIGHT,COMPRESS_SIZE_WIDTH);
                    verticalBitmap = BitmapUtils.rotateImage(realBitamp,90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    realBitamp = BitmapUtils.decodeFromFile(new File(getRealPathFromURI(cameiaImageUri)),COMPRESS_SIZE_WIDTH,COMPRESS_SIZE_HEIGHT);
                    verticalBitmap = BitmapUtils.rotateImage(realBitamp,180);
                    break;
                default:
                    realBitamp = BitmapUtils.decodeFromFile(new File(getRealPathFromURI(cameiaImageUri)),COMPRESS_SIZE_WIDTH,COMPRESS_SIZE_HEIGHT);
                    verticalBitmap = realBitamp;
                    break;
            }
            PCLog.i(TAG + "摄像头返回图片 Width " + realBitamp.getWidth() + " Height " + realBitamp.getHeight());
            PCLog.i(TAG+"旋转处理后图片 Width "+ verticalBitmap.getWidth()+" Height "+verticalBitmap.getHeight());
            return new PicsShare(verticalBitmap, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = mActivity.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            if (data != null){
                Uri uri = data.getData();
                if (uri != null && !uri.equals("")){
                    //拿到图片后需要进行压缩
                    Uri compressUri = BitmapUtils.compressBitmapFromUri(mActivity, uri, COMPRESS_SIZE_WIDTH, COMPRESS_SIZE_HEIGHT);
                    if (compressUri != null){
                        if (mPicActionListener != null){
                            mPicActionListener.addPic(new PicsShare(BitmapUtils.getBitmapFromUri(mActivity,compressUri),false));
                        }
                    }
                }
            }
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            PicsShare picsShare = receivePicFromCamera();
            if (mPicActionListener != null){
                mPicActionListener.addPic(picsShare);
            }
        } else if (requestCode == RESULT_DELETE_PICS) {
            PCLog.i(TAG+"result delete pics ");
            if (data != null){
                PCLog.i(TAG+" data != null ");
                ArrayList<Integer> picpositions = data.getIntegerArrayListExtra("picpositions");
                if (picpositions != null){
                    for (Integer i : picpositions){
                        PCLog.i(TAG+"删除图片 position "+i);
                    }
                    if (mPicActionListener != null) {
                        mPicActionListener.deletePic(picpositions);
                    }
                }
            }

        }
    }
}
