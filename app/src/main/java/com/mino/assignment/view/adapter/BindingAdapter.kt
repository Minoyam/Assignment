package com.mino.assignment.view.adapter

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.mino.assignment.utils.Event
import com.mino.assignment.R
import java.text.DecimalFormat

@BindingAdapter("image","imageSize")
fun ImageView.setImage(imageUri: String?,imageSize : Int) {
    imageUri?.let {
        if (it.isEmpty()) {
            Glide.with(context).load(R.drawable.ic_no_image).override(imageSize).into(this)
        } else {
                Glide.with(context).load(it).override(imageSize).into(this)
        }
    }
}
@BindingAdapter("likeImage")
fun ImageView.setLikeImage(isLike: Boolean) {
    if (isLike) {
        setImageResource(R.drawable.ic_favorite_24dp)
    } else {
        setImageResource(R.drawable.ic_favorite_border_24dp)
    }
}
@BindingAdapter("price")
fun TextView.setPrice(price: Int) {
    val formatter = DecimalFormat("###,###")
    text = resources.getString(R.string.binding_price,formatter.format(price))
}

@BindingAdapter("eventToString")
fun setEventToString(view: EditText, text: Event<String>?) {
    text?.getContentIfNotHandled()?.let {
        view.setText(it)
    }
}

@BindingAdapter("textAttrChanged")
fun setTextInverseBindingListener(view: EditText, listener: InverseBindingListener) {
    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            listener.onChange()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

@InverseBindingAdapter(attribute = "eventToString", event = "textAttrChanged")
fun getTextToEvent(view: EditText): Event<String> {
    return Event(view.text.toString())
}