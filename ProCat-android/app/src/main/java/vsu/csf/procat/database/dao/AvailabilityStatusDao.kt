package vsu.csf.procat.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import vsu.csf.procat.database.entity.AvailabilityStatus

@Dao
abstract class AvailabilityStatusDao {

    @Insert
    abstract fun save(availabilityStatus: AvailabilityStatus): Completable

    @Insert
    @Transaction
    fun saveList(availabilityStatuses: List<AvailabilityStatus>): Completable {
        return Completable.concat(
            listOf(clear()) + availabilityStatuses.map { save(it) }
        )
    }

    @Query("DELETE FROM AvailabilityStatus")
    abstract fun clear(): Completable

    @Query("SELECT * FROM AvailabilityStatus")
    abstract fun getAvailabilityStatuses(): Single<List<AvailabilityStatus>>

}