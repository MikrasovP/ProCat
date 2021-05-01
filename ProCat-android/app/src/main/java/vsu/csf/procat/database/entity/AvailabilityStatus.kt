package vsu.csf.procat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AvailabilityStatus(
    @PrimaryKey
    val id: Long,
    val name: String,
)
