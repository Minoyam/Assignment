package com.mino.assignment.di

import com.mino.assignment.data.source.remote.network.BookApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single<BookApi> {
        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
            .baseUrl(BookApi.BASE_URL)
            .build()
            .create(BookApi::class.java)
    }

    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }

    single<Converter.Factory> { GsonConverterFactory.create() }

    factory {
        OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                    .newBuilder()
                    .addHeader("Authorization", "KakaoAK ${BookApi.REST_API_KEY}")
                    .build()
                val response = it.proceed(request)
                response
            }.build()
    }
}