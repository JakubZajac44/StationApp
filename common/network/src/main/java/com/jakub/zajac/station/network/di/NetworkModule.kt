package com.jakub.zajac.station.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideBaseUrl(): String {
        return "https://koleo.pl/api/v2/main/"
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
    ) =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor())
            .build()


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

   class AuthInterceptor: Interceptor {
       override fun intercept(chain: Interceptor.Chain): Response {
           val requestBuilder: Request.Builder = chain.request().newBuilder()
           requestBuilder.header("X-KOLEO-Version", "1")
           return chain.proceed(requestBuilder.build())
       }

   }
}
