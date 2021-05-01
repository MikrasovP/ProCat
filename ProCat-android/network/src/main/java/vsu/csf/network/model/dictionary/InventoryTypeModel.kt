package vsu.csf.network.model.dictionary

import com.google.gson.annotations.SerializedName

data class InventoryTypeModel(
    @SerializedName("type_id")
    val id: Long,
    @SerializedName("type_name")
    val name: String,
)
