package com.eazywrite.app.util;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求拦截器
 */
public class RequestInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HttpUrl httpUrl = chain.request().url();
        String urlString = httpUrl.toString();
//        Log.d("RequestInterceptor", "request to url:" + urlString);

        //加上请求头参数
        builder.addHeader("x-ti-app-id","ac6d3377747e0712475f1bec56df83c1");
        builder.addHeader("x-ti-secret-code","541304425ad08c927cc6c87827412a5d");

        if(urlString.endsWith("ai/service/v1/crop_enhance_image")||//图像切边增强、裁切图像主体区域并增强api
                urlString.endsWith("/robot/v1.0/api/bills_crop")||//国内通用票据识别
        urlString.endsWith("/ai/service/v1/dewarp")){//图像切边矫正
            builder.addHeader("connection","Keep-Alive");
            builder.addHeader("Content-Type", "application/octet-stream");
        }

        return chain.proceed(builder.build());
    }
}
