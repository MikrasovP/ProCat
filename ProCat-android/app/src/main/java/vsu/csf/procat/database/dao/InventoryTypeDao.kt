package vsu.csf.procat.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import vsu.csf.procat.database.entity.InventoryType

@Dao
abstract class InventoryTypeDao {

    @Insert
    abstract fun save(inventoryType: InventoryType): Completable

    @Insert
    @Transaction
    fun saveList(inventoryTypes: List<InventoryType>): Completable {
        return Completable.concat(
            listOf(clear()) + inventoryTypes.map { save(it) }
        )
    }

    @Query("DELETE FROM InventoryType")
    abstract fun clear(): Completable

    @Query("SELECT * FROM InventoryType")
    abstract fun getInventoryTypes(): Single<List<InventoryType>>

}
