package com.example.pullcode.response.transaction


import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(

    @Expose
    @SerializedName("checkin")
    val checkin: String?,

    @Expose
    @SerializedName("created_at")
    val createdAt: Int?,

    @Expose
    @SerializedName("destinasi")
    val destinasi: Destinasi?,

    @Expose
    @SerializedName("destinasi_id")
    val destinasiId: Int?,

    @Expose
    @SerializedName("id")
    val id: Int?,

    @Expose
    @SerializedName("jam_terbang")
    val jamTerbang: String?,

    @Expose
    @SerializedName("nama_bandara")
    val namaBandara: String?,

    @Expose
    @SerializedName("payment_url")
    val paymentUrl: String?,

    @Expose
    @SerializedName("picture_pesawat")
    val picturePesawat: String?,

    @Expose
    @SerializedName("product")
    val product: Product?,

    @Expose
    @SerializedName("product_id")
    val productId: Int?,

    @Expose
    @SerializedName("provinsi")
    val provinsi: String?,

    @Expose
    @SerializedName("quantity")
    val quantity: Int?,

    @Expose
    @SerializedName("status")
    val status: String?,

    @Expose
    @SerializedName("total")
    val total: Int?,

    @Expose
    @SerializedName("updated_at")
    val updatedAt: Int?,

    @Expose
    @SerializedName("user")
    val user: User?,

    @Expose
    @SerializedName("picture_product")
    val pictureProduct: String?,

    @Expose
    @SerializedName("name")
    val name:String?,

    @Expose
    @SerializedName("rating")
    val rating:Double,

    @Expose
    @SerializedName("user_id")
    val userId: Int?
):Parcelable