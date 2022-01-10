package com.example.pullcode.response.bandara


import android.net.Uri
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    @Expose
    @SerializedName("created_at")
    val createdAt: Int?,

    @Expose
    @SerializedName("nama_bandara")
    val namaBandara: String?,

    @Expose
    @SerializedName("id")
    val id: Int?,

    @Expose
    @SerializedName("provinsi")
    val provinsi: String?,

    @Expose
    @SerializedName("picture_pesawat")
    val picturePesawat: String?,

    @Expose
    @SerializedName("jam_terbang")
    val jamTerbang:String?,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Int?
):Parcelable