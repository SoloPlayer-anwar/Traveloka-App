package com.example.pullcode.response.explore


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ExploreResponse(
    @Expose
    @SerializedName("data")
    val `data`: List<Data>,
    @Expose
    @SerializedName("meta")
    val meta: Meta
)

@Parcelize
data class Data(
    @Expose
    @SerializedName("address_destination")
    val addressDestination: String?,

    @Expose
    @SerializedName("created_at")
    val createdAt: Int?,

    @Expose
    @SerializedName("id")
    val id: Int?,

    @Expose
    @SerializedName("judul_guide")
    val judulGuide: String?,

    @Expose
    @SerializedName("name_guide")
    val nameGuide: String?,

    @Expose
    @SerializedName("photo_destination")
    val photoDestination: String?,

    @Expose
    @SerializedName("photo_guide")
    val photoGuide: String?,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Int?,

    @Expose
    @SerializedName("video")
    val video: String?
):Parcelable

data class Meta(
    @Expose
    @SerializedName("code")
    val code: Int,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("status")
    val status: String
)