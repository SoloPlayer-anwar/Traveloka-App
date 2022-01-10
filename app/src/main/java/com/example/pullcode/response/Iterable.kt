package com.example.pullcode.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Iterable<T>(
    @Expose
    @SerializedName("data")
    var data: T? = null,
    @Expose
    @SerializedName("meta")
    var meta: Meta? = null
)