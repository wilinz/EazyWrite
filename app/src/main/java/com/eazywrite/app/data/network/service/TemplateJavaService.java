package com.eazywrite.app.data.network.service;

import io.reactivex.rxjava3.core.Observable;

//这是一个java api 接口示例
public interface TemplateJavaService {
//    用rxjava包装好一点
    Observable<Object> getObject();
}
