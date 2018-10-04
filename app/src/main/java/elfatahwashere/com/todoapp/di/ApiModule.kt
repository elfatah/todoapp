package elfatahwashere.com.todoapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import elfatahwashere.com.todoapp.repo.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val apiModule = module {
    single { provideOkHttpClient() }
    single { provideGson() }
    single { provideApiService(get()) }
    single { provideConverterFactory(get()) }
    single { provideRetrofit(get(), get(), get()) }
    single { provideRxJavaCallAdapter() }
}

fun provideRetrofit(okHttpClient: OkHttpClient, factory: Converter.Factory, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory): Retrofit =
        Retrofit.Builder()
                .baseUrl("http://10.0.2.2")
                .client(okHttpClient)
                .addConverterFactory(factory)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .build()

fun provideApiService(retrofit: Retrofit): APIService =
        retrofit.create(APIService::class.java)

fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .writeTimeout(0, TimeUnit.MILLISECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
}

fun provideRxJavaCallAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()


fun provideConverterFactory(gson: Gson): Converter.Factory {
    return GsonConverterFactory.create(gson)
}

fun provideGson(): Gson {
    return GsonBuilder().create()
}
