package com.eazywrite.app.util;


import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * 访问相册或调用相机得到图片资源
 */
public class MediaUtil {
    public static final int TAKE_PHOTO = 6001;
    public static final int CHOOSE_PHOTO = 6002;
    private static String imagePath;

    public File getImage() throws Exception {
        if (imagePath==null||imagePath.equals("")) throw new Exception("图片不存在");
        return new File(imagePath);
    }

    private MediaUtil() {}
    private static MediaUtil mediaUtil;

    public static MediaUtil getInstance(){
        if(mediaUtil ==null){
            synchronized (MediaUtil.class){
                if(null== mediaUtil){
                    mediaUtil =new MediaUtil();
                }
            }
        }
        return mediaUtil;
    }

    /**
     * 打开相机拍照
     * @param context
     * @param activity
     */
    public void takePhoto(Context context, Activity activity) {
        File pictureFile = new File(context.getExternalCacheDir(), "takePicture.jpg");
        try {
            if (pictureFile.exists()) {
                pictureFile.delete();
            }
            pictureFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri imageUri = FileProvider.getUriForFile(context, "com.eazywrite.app.fileprovider", pictureFile);//通过内容提供器得到刚创建临时文件的Uri对象
        imagePath = pictureFile.getAbsolutePath();//得到该文件的绝对路径 imagePath为全局String
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");//打开相机
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//设置将拍好的相片存入刚创建的临时文件
        activity.startActivityForResult(intent, TAKE_PHOTO);//处理拍摄结果 这里TAKE_PHONE为定义好的一个全局变量
    }

    /**
     * 打开相册 选择图片
     * @param context
     * @param activity
     */
    public void openAlbum(Context context, Activity activity) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");//设置intent类型
        activity.startActivityForResult(intent, CHOOSE_PHOTO);//处理选择结果 这里CHOOSE_PHOTO为定义好的一个全局变量
    }

    /**
     * 处理从相册选择的图片
     * @param context
     * @param data
     */
    public static void handleImage(Context context, Intent data) {
        imagePath="";
        Uri uri = data.getData();
        //从android4.4开始选择相册中的图片不再返回图片的真实Uri了，而是一个封装的Uri，所以要对其进行解析
        if (DocumentsContract.isDocumentUri(context, uri)) {
            //如果是document类型的Uri
            String codeID = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                //如果uri的authority(柄)是media格式的话，得进一步解析
                String id = codeID.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(context,MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(codeID));
                imagePath = getImagePath(context,contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的uri用普通方法处理
            imagePath = getImagePath(context,uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是文件类型的uri可以直接获取图片路径
            imagePath = uri.getPath();
        }
    }

    /**
     * 通过图片Uri获取图片文件路径
     * @param context
     * @param uri
     * @param selection
     * @return
     */
    private static String getImagePath(Context context, Uri uri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));//MediaStore.Images.Media.DATA已弃用
            }
            cursor.close();
        }
        return path;
    }

}
