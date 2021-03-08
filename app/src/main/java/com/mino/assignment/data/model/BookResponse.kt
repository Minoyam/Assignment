package com.mino.assignment.data.model


import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("documents")
    val documents: List<DocumentResponse>,
    @SerializedName("meta")
    val meta: MetaResponse
)