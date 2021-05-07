package vsu.csf.network.model.dictionary

import com.google.gson.annotations.SerializedName

data class InventoryTypeModel(
    @SerializedName("typeId")
    val id: Long,
    @SerializedName("typeName")
    val name: String,
)
