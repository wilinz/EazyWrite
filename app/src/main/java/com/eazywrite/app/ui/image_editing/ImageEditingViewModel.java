package com.eazywrite.app.ui.image_editing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eazywrite.app.data.repository.ImageEditRepository;
import com.eazywrite.app.util.MediaUtil;
import com.eazywrite.app.util.PermissionUtil;

import java.io.File;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ImageEditingViewModel extends ViewModel {

    public MutableLiveData<File> imageFile = new MutableLiveData<>();
    public MutableLiveData<File> editedImage = new MutableLiveData<>();

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


    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void cropEnhanceImage() {
        ImageEditRepository.getInstance().getCropEnhanceImageResponse(imageFile.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (imageEditResponse) -> {
                            editedImage.setValue(MediaUtil.getInstance().base64ToFile(imageEditResponse.getResult().getImageList().get(0).getImage()));
                            },
                        (e) -> {
                            Log.d("Debug","cropEnhanceImage error");
                        },
                        () -> {
                        }
                );
    }

    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void dewarpImage(){
        ImageEditRepository.getInstance().getDewarpResponse(imageFile.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (dewarpResponse) -> {
                            editedImage.setValue(MediaUtil.getInstance().base64ToFile(dewarpResponse.getResult().getImage()));
                        },
                        (e) -> {
                            Log.d("Debug","dewarpImage error");
                        },
                        () -> {
                        }
                );
    }

}
