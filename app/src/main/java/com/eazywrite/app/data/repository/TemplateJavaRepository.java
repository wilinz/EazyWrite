package com.eazywrite.app.data.repository;

import com.eazywrite.app.data.network.Network;

import io.reactivex.rxjava3.core.Observable;

//这是一个java Repository模板示例
public class TemplateJavaRepository {

    Object cache = null;

    Observable<Object> getObject() {
        if (cache != null) return Observable.just(cache);
        return Network.INSTANCE.getTemplateJavaService().getObject().doOnNext((obj -> cache = obj));
    }

    private TemplateJavaRepository() {

    }

    private static volatile TemplateJavaRepository instance;

    static TemplateJavaRepository getInstance() {
        if (instance == null) {
            synchronized (TemplateJavaRepository.class) {
                if (instance == null) {
                    instance = new TemplateJavaRepository();
                }
            }
        }
        return instance;
    }
}
