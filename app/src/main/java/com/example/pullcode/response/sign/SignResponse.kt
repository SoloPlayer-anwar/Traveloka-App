package com.example.pullcode.response.sign

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SignResponse(
    @Expose
    @SerializedName("access_token")
    val access_token: String,
    @Expose
    @SerializedName("token_type")
    val token_type: String,
    @Expose
    @SerializedName("user")
    val user: User
)