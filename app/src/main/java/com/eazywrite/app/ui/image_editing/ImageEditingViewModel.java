package com.eazywrite.app.ui.image_editing;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.eazywrite.app.data.model.BillsCropResponse;
import com.eazywrite.app.data.repository.ImageEditRepository;
import com.eazywrite.app.util.Base64UtilKt;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ImageEditingViewModel extends AndroidViewModel {

    public MutableLiveData<File> imageFile = new MutableLiveData<>();
    public MutableLiveData<List<BillsCropResponse.ResultDTO.ObjectListDTO>> tickList = new MutableLiveData<>();
    public MutableLiveData<File> editedImage = new MutableLiveData<>();

    public ImageEditingViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public void cropEnhanceImage() {
        ImageEditRepository.getInstance().getCropEnhanceImageResponse(imageFile.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (imageEditResponse) -> {
                            File pictureFile = new File(getApplication().getCacheDir(), "editedPicture.jpg");
                            imageFile.setValue(Base64UtilKt.base64ToFile(pictureFile, imageEditResponse.getResult().getImageList().get(0).getImage()));
                        },
                        (e) -> {
                            Log.d("Debug", "cropEnhanceImage error");
                        },
                        () -> {
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void dewarpImage() {
        ImageEditRepository.getInstance().getDewarpResponse(imageFile.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (dewarpResponse) -> {
                            File pictureFile = new File(getApplication().getCacheDir(), "editedPicture.jpg");
                            File file = Base64UtilKt.base64ToFile(pictureFile, dewarpResponse.getResult().getImage());
                            imageFile.setValue(file);
                            editedImage.setValue(file);
                        },
                        (e) -> {
                            Log.d("Debug", "dewarpImage error");
                        },
                        () -> {
                        }
                );
    }

    @SuppressLint("CheckResult")
    public void getBills() {
        ImageEditRepository.getInstance().getBillsCropResponse(imageFile.getValue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (billsCropResponse) -> {
                            /**票据List**/
                            List<BillsCropResponse.ResultDTO.ObjectListDTO> ticksList = billsCropResponse.getResult().getObjectList();
                            Log.d("Bills by Image", "一共识别出 " + ticksList.size() + " 张票据");
                            /**每张票据的详细内容**/
                            //List<BillsCropResponse.ResultDTO.ObjectListDTO.ItemListDTO> tickItemList=ticksList.get(0).getItemList();
                            for (BillsCropResponse.ResultDTO.ObjectListDTO objectListDTO : ticksList) {
                                Log.d("Bills by Image", "===这是一张：" + objectListDTO.getTypeDescription() + "===");
                                for (BillsCropResponse.ResultDTO.ObjectListDTO.ItemListDTO itemListDTO : objectListDTO.getItemList()) {
                                    Log.d("Bills by Image", itemListDTO.getDescription() + " : " + itemListDTO.getValue());
                                }
                            }
                            tickList.setValue(ticksList);
                        },
                        (e) -> {
                        },
                        () -> {
                        }
                );
    }
}
