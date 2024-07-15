package com.example.githubbrowser.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.githubbrowser.data.network.GithubApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): GithubApi =
        retrofit.create(GithubApi::class.java)


    @Provides
    @Singleton
    fun provideChuckerInterceptor(@ApplicationContext context: Context): ChuckerInterceptor = ChuckerInterceptor.Builder(context)
        .maxContentLength(250_000L)
        .redactHeaders("Auth-Token", "Bearer")
        .alwaysReadResponseBody(true)
        .createShortcut(true)
        .build()


    @Provides
    @Singleton
    fun provideOkHttp(
        chuckerInterceptor: ChuckerInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .callTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitApi(okHttpClient: OkHttpClient): Retrofit {
        val json = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .baseUrl(URL_API_GITHUB)
            .build()
    }

    private const val URL_API_GITHUB = "https://api.github.com/"
    private const val CONTENT_TYPE = "application/vnd.github+json"
}