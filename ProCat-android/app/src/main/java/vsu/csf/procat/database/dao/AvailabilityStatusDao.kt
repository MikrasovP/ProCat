package vsu.csf.procat.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import vsu.csf.procat.database.entity.AvailabilityStatus

@Dao
abstract class AvailabilityStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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

    @Query("SELECT name FROM AvailabilityStatus WHERE id=:statusId")
    abstract fun getNameById(statusId: Long): Maybe<String>

    @Query("SELECT * FROM AvailabilityStatus")
    abstract fun getAvailabilityStatuses(): Single<List<AvailabilityStatus>>

    @Query("SELECT name FROM AvailabilityStatus")
    abstract fun getAvailabilityStatusesNames(): Single<List<String>>

}