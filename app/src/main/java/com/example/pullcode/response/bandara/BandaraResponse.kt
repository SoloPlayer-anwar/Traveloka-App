package com.example.pullcode.response.bandara


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BandaraResponse(
    @Expose
    @SerializedName("data")
    val `data`: List<Data>,
    @Expose
    @SerializedName("meta")
    val meta: Meta
)