package com.mino.assignment.data.model.response


import com.google.gson.annotations.SerializedName
import com.mino.assignment.data.model.DocumentModel

data class DocumentResponse(
    @SerializedName("authors")
    val authors: List<String>,
    @SerializedName("contents")
    val contents: String,
    @SerializedName("datetime")
    val datetime: String,
    @SerializedName("isbn")
    val isbn: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("publisher")
    val publisher: String,
    @SerializedName("sale_price")
    val salePrice: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("translators")
    val translators: List<String>,
    @SerializedName("url")
    val url: String
) {
    fun toModel(): DocumentModel {
        return DocumentModel(
            contents = contents,
            datetime = if (datetime.isEmpty()) datetime else datetime.substring(0, 10),
            price = price,
            thumbnail = thumbnail,
            title = title,
            publisher = publisher
        )
    }
}