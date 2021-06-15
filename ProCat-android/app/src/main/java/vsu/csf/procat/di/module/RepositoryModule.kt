package vsu.csf.procat.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vsu.csf.procat.repo.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindDictionariesRepo(impl: DictionariesRepoImpl): DictionariesRepo

    @Singleton
    @Binds
    abstract fun bindRentStationsRepo(impl: RentStationsRepoImpl): RentStationsRepo

    @Singleton
    @Binds
    abstract fun bindRentRepo(impl: RentRepoImpl): RentRepo

    @Singleton
    @Binds
    abstract fun bindAuthRepo(impl: AuthRepoImpl): AuthRepo

}