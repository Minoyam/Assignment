package com.mino.assignment.view.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mino.assignment.R
import com.mino.assignment.data.model.DocumentModel
import java.text.DecimalFormat

@BindingAdapter("bind:bindImage")
fun ImageView.bindImage(imageUri: String?) {
    imageUri?.let {
        if (it.isEmpty()) {
            Glide.with(context).load(R.drawable.ic_no_image).override(200, 300).into(this)
        } else {
                Glide.with(context).load(it).override(200, 300).into(this)
        }
    }
}
@BindingAdapter("bind:bindImageDetail")
fun ImageView.bindImageDetail(imageUri: String?) {
    imageUri?.let {
        if (it.isEmpty()) {
            Glide.with(context).load(R.drawable.ic_no_image).override(400).into(this)
        } else {
            Glide.with(context).load(it).override(400).into(this)
        }
    }

}

@BindingAdapter("bind:bindLikeImage")
fun ImageView.bindLikeImage(isLike: Boolean) {
    if (isLike) {
        setImageResource(R.drawable.ic_favorite_24dp)
    } else {
        setImageResource(R.drawable.ic_favorite_border_24dp)
    }
}

@BindingAdapter("bind:bindSetBookItemList", "bind:page")
fun RecyclerView.bindSetBookItemList(items: List<DocumentModel>?, page: Int) {
    if (adapter is BookListAdapter)
        items?.let {
            if (page != 1) {
                (adapter as BookListAdapter).setPagingDocumentModel(it)
            } else {
                (adapter as BookListAdapter).setDocumentModel(it)
            }
        }
}

@BindingAdapter("bind:bindSetPrice")
fun TextView.bindSetPrice(price: Int) {
    val formatter = DecimalFormat("###,###")
    text = resources.getString(R.string.binding_price,formatter.format(price))
}