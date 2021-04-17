package vsu.csf.procat.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import vsu.csf.procat.database.ProCatDatabase
import vsu.csf.procat.database.dao.AvailabilityStatusDao
import vsu.csf.procat.database.dao.InventoryTypeDao
import vsu.csf.procat.database.dao.RentStatusDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {
    companion object {
        private const val DATABASE_FILE = "procat.db"

        @Singleton
        @Provides
        fun provideAvailabilityStatusDao(database: ProCatDatabase): AvailabilityStatusDao =
            database.availabilityStatusDao()
        @Singleton
        @Provides
        fun provideInventoryTypeDao(database: ProCatDatabase): InventoryTypeDao =
            database.inventoryTypeDao()
        @Singleton
        @Provides
        fun provideRentStatusDao(database: ProCatDatabase): RentStatusDao =
            database.rentStatusDao()

        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context): ProCatDatabase =
            Room.databaseBuilder(context, ProCatDatabase::class.java, DATABASE_FILE)
                .fallbackToDestructiveMigration()
                .build()

    }
}