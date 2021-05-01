package vsu.csf.procat.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import vsu.csf.procat.database.entity.RentStatus

@Dao
abstract class RentStatusDao {
    @Insert
    abstract fun save(rentStatus: RentStatus): Completable

    @Insert
    @Transaction
    fun saveList(rentStatuses: List<RentStatus>): Completable {
        return Completable.concat(
            listOf(clear()) + rentStatuses.map { save(it) }
        )
    }

    @Query("DELETE FROM RentStatus")
    abstract fun clear(): Completable

    @Query("SELECT * FROM RentStatus")
    abstract fun getRentStatuses(): Single<List<RentStatus>>
}