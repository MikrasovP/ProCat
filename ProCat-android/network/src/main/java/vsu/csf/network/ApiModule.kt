package vsu.csf.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import vsu.csf.network.api.DictionaryApi
import vsu.csf.network.api.RentStationApi
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ApiModule {
    companion object {

        @Singleton
        @Provides
        fun provideRentStationApi(retrofit: Retrofit): RentStationApi =
            retrofit.create(RentStationApi::class.java)

        @Singleton
        @Provides
        fun provideDictionaryApi(retrofit: Retrofit): DictionaryApi =
            retrofit.create(DictionaryApi::class.java)

    }
}