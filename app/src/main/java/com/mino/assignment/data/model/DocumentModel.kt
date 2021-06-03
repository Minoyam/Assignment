package com.mino.assignment.data.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DocumentModel(
    val contents: String,
    val datetime: String,
    val price: Int,
    val thumbnail: String,
    val title: String,
    val publisher : String,
    var like: Boolean = false
) : Parcelable
