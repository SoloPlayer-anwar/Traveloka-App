package com.example.pullcode.response.checkout


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(

    @Expose
    @SerializedName("address")
    val address: Any,

    @Expose
    @SerializedName("city")
    val city: Any,

    @Expose
    @SerializedName("created_at")
    val createdAt: Int,

    @Expose
    @SerializedName("current_team_id")
    val currentTeamId: Any,

    @Expose
    @SerializedName("email")
    val email: String,

    @Expose
    @SerializedName("email_verified_at")
    val emailVerifiedAt: Any,

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
    val profilePhotoPath: String,

    @Expose
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Int
)