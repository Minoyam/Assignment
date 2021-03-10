package com.mino.assignment.data.model.response


import com.google.gson.annotations.SerializedName
import com.mino.assignment.data.model.MetaModel

data class MetaResponse(
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("total_count")
    val totalCount: Int
){
    fun toModel() = MetaModel(
        isEnd = isEnd,
        pageableCount = pageableCount,
        totalCount = totalCount
    )
}