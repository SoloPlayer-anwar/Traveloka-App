package com.example.pullcode.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Chat (
    var pesan: String? = "",
    var waktu: String? = "",
    var nameProduct: String? = "",
    var priceProduct: Int? = null,
    var productImage: String? = "",
    var nameUser: String? = "",
):Parcelable