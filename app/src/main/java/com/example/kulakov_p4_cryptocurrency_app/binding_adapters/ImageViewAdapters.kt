package com.example.kulakov_p4_cryptocurrency_app.binding_adapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.kulakov_p4_cryptocurrency_app.R
import com.squareup.picasso.Picasso

@BindingAdapter(value=["srcUrl", "resizeWidth", "resizeHeight"], requireAll = false)
fun bindPhoto(view: ImageView, photo: Any?, resizeWidth: Int? = null, resizeHeight: Int? = null) {
    if(photo is Bitmap) view.setImageBitmap(photo)
    else if(photo is Drawable) view.setImageDrawable(photo)
    else if(photo is String) {
        var builder = Picasso.get().load(photo)

        if(resizeHeight != null && resizeWidth != null) builder = builder.resize(100, 100)

        builder.placeholder(R.drawable.no_photo)
            .error(R.drawable.no_photo)
            .into(view)
    }
}