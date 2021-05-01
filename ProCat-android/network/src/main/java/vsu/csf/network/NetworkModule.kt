package vsu.csf.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {

        @Singleton
        @Provides
        fun provideRetrofit(
            httpClient: OkHttpClient,
        ): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        @Singleton
        @Provides
        fun provideHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            authInterceptor: UserAuthInterceptor,
        ) = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        @Singleton
        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

        @Singleton
        @Provides
        fun provideAuthInterceptor(authHolder: AuthHolder) = UserAuthInterceptor(authHolder)

    }
}