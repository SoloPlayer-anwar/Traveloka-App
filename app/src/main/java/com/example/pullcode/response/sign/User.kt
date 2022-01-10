package com.example.pullcode.response.sign

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @SerializedName("address")
    val address: String,

    @Expose
    @SerializedName("city")
    val city: String,

    @Expose
    @SerializedName("created_at")
    val created_at: Int,

    @Expose
    @SerializedName("current_team_id")
    val current_team_id: Any,

    @Expose
    @SerializedName("email")
    val email: String,

    @Expose
    @SerializedName("email_verified_at")
    val email_verified_at: Any,

    @Expose
    @SerializedName("id")
    val id: Int,

    @Expose
    @SerializedName("level")
    val level: String,

    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("phone")
    val phone: String,

    @Expose
    @SerializedName("profile_photo_path")
    val profile_photo_path: Any,

    @Expose
    @SerializedName("profile_photo_url")
    val profile_photo_url: String,

    @Expose
    @SerializedName("updated_at")
    val updated_at: Int
)