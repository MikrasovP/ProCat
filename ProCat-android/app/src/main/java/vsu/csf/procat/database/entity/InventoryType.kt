package vsu.csf.procat.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InventoryType(
    @PrimaryKey
    val id: Long,
    val name: String,
)
