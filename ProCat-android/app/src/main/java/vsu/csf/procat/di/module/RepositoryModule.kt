package vsu.csf.procat.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import vsu.csf.procat.repo.*


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindDictionariesRepo(impl: DictionariesRepoImpl): DictionariesRepo

    @ActivityRetainedScoped
    @Binds
    abstract fun bindRentStationsRepo(impl: RentStationsRepoImpl): RentStationsRepo

    @ActivityRetainedScoped
    @Binds
    abstract fun bindRentRepo(impl: RentRepoImpl): RentRepo

    @ActivityRetainedScoped
    @Binds
    abstract fun bindAuthRepo(impl: AuthRepoImpl): AuthRepo

}