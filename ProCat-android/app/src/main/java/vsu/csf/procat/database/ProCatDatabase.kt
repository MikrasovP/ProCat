package vsu.csf.procat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import vsu.csf.procat.database.dao.AvailabilityStatusDao
import vsu.csf.procat.database.dao.InventoryTypeDao
import vsu.csf.procat.database.dao.RentStatusDao
import vsu.csf.procat.database.entity.AvailabilityStatus
import vsu.csf.procat.database.entity.InventoryType
import vsu.csf.procat.database.entity.RentStatus

@Database(
    version = 1,
    entities = [
        AvailabilityStatus::class,
        InventoryType::class,
        RentStatus::class,
    ],
)
abstract class ProCatDatabase : RoomDatabase() {

    abstract fun availabilityStatusDao(): AvailabilityStatusDao
    abstract fun inventoryTypeDao(): InventoryTypeDao
    abstract fun rentStatusDao(): RentStatusDao

}