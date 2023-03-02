package com.eazywrite.app.data.database.network

import com.eazywrite.app.BuildConfig
import com.eazywrite.app.MyApplication
import com.eazywrite.app.data.database.network.service.AccountService
import com.eazywrite.app.data.database.network.service.TemplateJavaService
import com.eazywrite.app.data.database.network.service.TemplateKotlinService
import com.eazywrite.app.util.RequestInterceptor
import com.thomasbouvier.persistentcookiejar.PersistentCookieJar
import com.thomasbouvier.persistentcookiejar.cache.SetCookieCache
import com.thomasbouvier.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    var cookieJar =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(MyApplication.instance))

    private val baseOkhttpClientBuilder get() = OkHttpClient.Builder().apply {
        cookieJar(cookieJar)
        this.addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        })
        this.callTimeout(10, TimeUnit.SECONDS)
        this.connectTimeout(10, TimeUnit.SECONDS)
        this.readTimeout(10, TimeUnit.SECONDS)
        this.writeTimeout(10, TimeUnit.SECONDS)
    }

    private val baseRetrofitBuilder get() = Retrofit.Builder()
        .client(baseOkhttpClientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())

    private val retrofit = baseRetrofitBuilder
        .client(baseOkhttpClientBuilder.apply {
            this.addInterceptor(RequestInterceptor())//请求拦截器
        }.build())
        .baseUrl("https://api.textin.com")
        .build()

    private val loginRetrofit = baseRetrofitBuilder.baseUrl("https://hw.wilinz.com:444").build()

    val templateJavaService = retrofit.create<TemplateJavaService>()

    val templateKotlinService = retrofit.create<TemplateKotlinService>()

    val accountService = loginRetrofit.create<AccountService>()
    private inline fun <reified T> Retrofit.create() = create(T::class.java)


}