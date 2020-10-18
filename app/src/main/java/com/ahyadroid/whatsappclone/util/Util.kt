package com.ahyadroid.whatsappclone.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.ahyadroid.whatsappclone.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun populateImage( // fungsi populateImage digunakan untuk memudahkan proses
    context: Context?, // pemasangan gambar ke dalam ImageView dengan menggunakan
    uri: String?, // Library pihak ketiga yaitu Glide
    imageView: ImageView,
    errorDrawable: Int = R.drawable.empty
) {
    if (context != null) {
        val options =
            RequestOptions().placeholder(progressDrawable(context)).error(errorDrawable)
        Glide.with(context).load(uri).apply(options).into(imageView)
    }
}
// menambahkan progressDrawable ketika Image dalam proses pemasangan
fun progressDrawable(context: Context): CircularProgressDrawable { // berupa lingkaran
    return CircularProgressDrawable(context).apply{
        strokeWidth = 5f // ketebalan garis lingkaran
        centerRadius = 30f // diameter lingkaran
        start() // memulai progressDrawable
    }
}