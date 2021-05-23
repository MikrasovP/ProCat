package vsu.csf.procat.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vsu.csf.network.AuthHolder
import vsu.csf.procat.utils.AuthHolderImpl
import vsu.csf.procat.utils.prefs.SharedPrefsRepo
import vsu.csf.procat.utils.prefs.SharedPrefsRepoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    @Singleton
    @Binds
    abstract fun bindAuthHolder(impl: AuthHolderImpl): AuthHolder

    @Singleton
    @Binds
    abstract fun bindPrefsRepo(prefsRepoImpl: SharedPrefsRepoImpl): SharedPrefsRepo

}