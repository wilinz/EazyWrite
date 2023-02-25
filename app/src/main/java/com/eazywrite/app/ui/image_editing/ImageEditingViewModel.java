package com.eazywrite.app.ui.image_editing;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eazywrite.app.util.MediaUtil;
import com.eazywrite.app.util.PermissionUtil;

public class ImageEditingViewModel extends ViewModel {

    public MutableLiveData<String> imagePath = new MutableLiveData<>();

    /**
     * 动态请求权限
     *
     * @param isTakePhoto 是否需要调用相机
     */
    public void requestPermissions(boolean isTakePhoto, Activity activity, Context context) {
        boolean b = PermissionUtil.getInstance().checkPermission(activity, context, PermissionUtil.READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (isTakePhoto) {
            boolean b1 = PermissionUtil.getInstance().checkPermission(activity, context, PermissionUtil.CAMERA, Manifest.permission.CAMERA);
            if (b && b1) takePhoto(context, activity);
        } else {
            if (b) chosePhone(context, activity);
        }
    }

    public void takePhoto(Context context, Activity activity) {
        MediaUtil.getInstance().takePhoto(context, activity);
    }

    public void chosePhone(Context context, Activity activity) {
        MediaUtil.getInstance().openAlbum(context, activity);
    }
}
