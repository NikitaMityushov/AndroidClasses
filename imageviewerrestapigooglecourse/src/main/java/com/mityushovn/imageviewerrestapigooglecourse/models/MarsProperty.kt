package com.mityushovn.imageviewerrestapigooglecourse.models

import com.squareup.moshi.Json
import java.io.Serializable

data class MarsProperty(
    val id: String,
    // used to map img_src from the JSON to imgSrcUrl in our class
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double
) : Serializable