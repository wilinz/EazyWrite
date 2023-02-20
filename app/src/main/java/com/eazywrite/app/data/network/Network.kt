package com.eazywrite.app.data.network

import com.eazywrite.app.BuildConfig
import com.eazywrite.app.MyApplication
import com.eazywrite.app.data.network.service.TemplateJavaService
import com.eazywrite.app.data.network.service.TemplateKotlinService
import com.thomasbouvier.persistentcookiejar.PersistentCookieJar
import com.thomasbouvier.persistentcookiejar.cache.SetCookieCache
import com.thomasbouvier.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    var cookieJar =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApplication.instance))

    private val retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().apply {
            cookieJar(cookieJar)
            this.addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            this.callTimeout(10, TimeUnit.SECONDS)
            this.connectTimeout(10, TimeUnit.SECONDS)
            this.readTimeout(10, TimeUnit.SECONDS)
            this.writeTimeout(10, TimeUnit.SECONDS)
        }.build())
        .baseUrl("https://xxx")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    val templateJavaService = retrofit.create<TemplateJavaService>()

    val templateKotlinService = retrofit.create<TemplateKotlinService>()

    private inline fun <reified T> Retrofit.create() = create(T::class.java)
}