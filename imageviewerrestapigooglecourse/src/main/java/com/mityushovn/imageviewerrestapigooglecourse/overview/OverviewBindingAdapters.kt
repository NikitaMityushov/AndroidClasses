package com.mityushovn.imageviewerrestapigooglecourse.overview

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mityushovn.imageviewerrestapigooglecourse.R
import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty

@BindingAdapter("bindImage")
fun bindImage(iv: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(iv.context)
            .load(imgUri)
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(iv)
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(rv: RecyclerView, data: List<MarsProperty>) {
    val adapter = OverviewListAdapter(data, clickListener = (MarsItemListener {
        rv.findNavController()
            .navigate(OverviewFragmentDirections.actionOverviewFragmentToAboutFragment())
    }))
    rv.adapter = adapter
}

@BindingAdapter("marsApiStatus")
fun bindStatus(statusIV: ImageView, status: MarsApiStatus) {
    when (status) {

        MarsApiStatus.LOADING -> {
            statusIV.visibility = View.VISIBLE
            statusIV.setImageResource(R.drawable.loading_animation)
        }

        MarsApiStatus.ERROR -> {
            statusIV.visibility = View.VISIBLE
            statusIV.setImageResource(R.drawable.ic_connection_error)
        }

        MarsApiStatus.DONE -> {
            statusIV.visibility = View.GONE

        }
    }
}