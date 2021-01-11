package com.doubleslas.fifith.alcohol.model.network.base

import com.doubleslas.fifith.alcohol.App
import com.doubleslas.fifith.alcohol.BuildConfig
import com.doubleslas.fifith.alcohol.model.network.AuthRetrofit
import com.doubleslas.fifith.alcohol.model.network.SearchRetrofit
import com.doubleslas.fifith.alcohol.model.network.dto.DetailRetrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {
    fun getDetailService(): DetailRetrofit = retrofit.create(DetailRetrofit::class.java)
    fun getAuthService(): AuthRetrofit = retrofit.create(AuthRetrofit::class.java)
    fun getSearchService(): SearchRetrofit = retrofit.create(SearchRetrofit::class.java)

    private val retrofit =
        Retrofit.Builder().run {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

            val headerInterceptor = object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request: Request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer ${App.prefs.idToken}")
                        .build()
                    return chain.proceed(request)
                }
            }

            val client = OkHttpClient.Builder().run {
                addInterceptor(loggingInterceptor)
                addInterceptor(headerInterceptor)
                build()
            }

//            baseUrl("http://192.168.0.9:3000") // 도메인 주소
            baseUrl("http://double-slash.shop/") // 도메인 주소
            client(client)

            addCallAdapterFactory(LiveDataCallAdapter.Factory())
            addConverterFactory(GsonConverterFactory.create()) // GSON을 사요아기 위해 ConverterFactory에 GSON 지정
            build()
        }
}

