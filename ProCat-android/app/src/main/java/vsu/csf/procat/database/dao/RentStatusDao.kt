package vsu.csf.procat.database.dao

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import vsu.csf.procat.database.entity.RentStatus

@Dao
abstract class RentStatusDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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

    @Query("SELECT name FROM RentStatus WHERE id=:statusId")
    abstract fun getNameById(statusId: Long): Maybe<String>

    @Query("SELECT * FROM RentStatus")
    abstract fun getRentStatuses(): Single<List<RentStatus>>

    @Query("SELECT name FROM RentStatus")
    abstract fun getRentStatusesNames(): Single<List<String>>

}