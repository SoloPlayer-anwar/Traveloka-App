package com.example.pullcode.response.checkout


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Product(

    @Expose
    @SerializedName("category")
    val category: String,

    @Expose
    @SerializedName("created_at")
    val createdAt: Int,

    @Expose
    @SerializedName("description")
    val description: String,

    @Expose
    @SerializedName("expired")
    val expired: String,

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("lat")
    val lat: Double,

    @Expose
    @SerializedName("long")
    val long: Double,

    @Expose
    @SerializedName("map")
    val map: String,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("picture_path")
    val picturePath: String,

    @Expose
    @SerializedName("place")
    val place: String,

    @Expose
    @SerializedName("price")
    val price: Int,

    @Expose
    @SerializedName("rate")
    val rate: Double,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Int
)