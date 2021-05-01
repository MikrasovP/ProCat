package vsu.csf.procat.auth

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import vsu.csf.network.AuthHolder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Singleton
    @Binds
    abstract fun bindAuthHolder(impl: AuthHolderImpl): AuthHolder

}