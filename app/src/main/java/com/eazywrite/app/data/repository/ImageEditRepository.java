package com.eazywrite.app.data.repository;

import com.eazywrite.app.data.model.BillsCropResponse;
import com.eazywrite.app.data.model.CropEnhanceImageResponse;
import com.eazywrite.app.data.model.DewarpResponse;
import com.eazywrite.app.data.network.Network;

import java.io.File;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

//图片增强仓库
public class ImageEditRepository {

    private ImageEditRepository() {
    }

    private static volatile ImageEditRepository instance;

    public static ImageEditRepository getInstance() {
        if (instance == null) {
            synchronized (ImageEditRepository.class) {
                if (instance == null) {
                    instance = new ImageEditRepository();
                }
            }
        }
        return instance;
    }



    public Observable<CropEnhanceImageResponse> getCropEnhanceImageResponse(File imageFile) {
        RequestBody body=RequestBody.Companion.create(imageFile,MediaType.parse("application/octet-stream"));
        return Network.INSTANCE.getTemplateJavaService().cropEnhanceImage(2, 1,body);
    }

    public Observable<DewarpResponse> getDewarpResponse(File imageFile){
        RequestBody body=RequestBody.Companion.create(imageFile,MediaType.parse("application/octet-stream"));
        return Network.INSTANCE.getTemplateJavaService().dewarp(1, 1,body);
    }

    public Observable<BillsCropResponse> getBillsCropResponse(File imageFile){
        RequestBody body=RequestBody.Companion.create(imageFile,MediaType.parse("application/octet-stream"));
        return Network.INSTANCE.getTemplateJavaService().billsCrop(body);
    }

}
