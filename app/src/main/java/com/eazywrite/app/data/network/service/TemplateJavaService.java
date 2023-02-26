package com.eazywrite.app.data.network.service;

import com.eazywrite.app.data.model.CropEnhanceImageResponse;
import com.eazywrite.app.data.model.DewarpResponse;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

//这是一个java api 接口示例
public interface TemplateJavaService {
    //    用rxjava包装好一点
    Observable<Object> getObject();

    /**
     * 功能描述:
     * 图像切边增强
     * 裁切图像主体区域并增强
     *
     * @param crop_image        0 不执行切边操作
     *                          1 执行切边操作，默认为1
     * @param correct_direction 0 不校正图片方向，默认为0
     *                          1 校正图片方向
     * @return
     */
    @POST("/ai/service/v1/crop_enhance_image")
    Observable<CropEnhanceImageResponse> cropEnhanceImage(@Query("crop_image") int crop_image,
                                                          @Query("correct_direction") int correct_direction,
                                                          @Body RequestBody body);

    /**
     * 功能描述
     * 图像切边矫正
     * <p>
     * 文档提取-->形变矫正-->边缘填充
     *
     * crop 是否切边（即文档提取）；0不切边；1切边
     * inpainting 是否边缘填充； 0不填充；1填充
     */
    @POST("/ai/service/v1/dewarp")
    Observable<DewarpResponse> dewarp(@Query("crop") int crop,
                                      @Query("inpainting") int inpainting,
                                      @Body RequestBody body);
}
