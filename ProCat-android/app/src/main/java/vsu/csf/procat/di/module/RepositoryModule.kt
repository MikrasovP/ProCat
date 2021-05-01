package vsu.csf.procat.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import vsu.csf.procat.repo.DictionariesRepo
import vsu.csf.procat.repo.DictionariesRepoImpl
import vsu.csf.procat.repo.RentStationsRepo
import vsu.csf.procat.repo.RentStationsRepoImpl


@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @ActivityRetainedScoped
    @Binds
    abstract fun bindDictionariesRepo(impl: DictionariesRepoImpl): DictionariesRepo

    @ActivityRetainedScoped
    @Binds
    abstract fun bindRentStationsRepo(impl: RentStationsRepoImpl): RentStationsRepo

}