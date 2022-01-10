package com.example.pullcode.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Chat (
    var nameUser: String? = "",
    var pesan: String? = "",
    var waktu: String? = "",
    var productImage: String? = "",
    var nameProduct: String? = "",
    var priceProduct: String? = ""
):Parcelable