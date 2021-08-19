package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.R
import com.squareup.picasso.Picasso

@BindingAdapter(value=["srcUrl", "resizeWidth", "resizeHeight"], requireAll = false)
fun bindPhoto(view: ImageView, url: String?, resizeWidth: Int? = null, resizeHeight: Int? = null) {
    var builder = Picasso.get().load(url)

    if(resizeHeight != null && resizeWidth != null) builder = builder.resize(100, 100)

    builder.placeholder(R.drawable.no_photo)
        .error(R.drawable.no_photo)
        .into(view)
}