package com.example.pullcode.network

import com.example.pullcode.response.Iterable
import com.example.pullcode.response.bandara.BandaraResponse
import com.example.pullcode.response.checkout.CheckoutResponse
import com.example.pullcode.response.destinasi.DestinasiResponse
import com.example.pullcode.response.explore.ExploreResponse
import com.example.pullcode.response.product.ProductResponse
import com.example.pullcode.response.sign.SignResponse
import com.example.pullcode.response.transaction.Data
import com.example.pullcode.response.transaction.TransactionResponse
import io.reactivex.Observable
import retrofit2.http.*

interface Endpoint {
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("email")email:String,
              @Field("password")password:String):Observable<Iterable<SignResponse>>

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("name") name:String,
                 @Field("email")email:String,
                 @Field("password") password:String,
                 @Field("password_confirmation") passwordConfirm:String,
                 @Field("address")address:String,
                 @Field("phone")phone:String,
                 @Field("city")city:String):Observable<Iterable<SignResponse>>

    @GET("product")
    fun product(@Query("limit") limit: Int):Observable<Iterable<ProductResponse>>

    @GET("destinasi")
    fun destination():Observable<Iterable<DestinasiResponse>>

    @GET("bandara")
    fun airport():Observable<BandaraResponse>


    @FormUrlEncoded
    @POST("checkout")
    fun checkoutDestination(@Field("user_id") UserId: Int,
                            @Field("quantity") quantity: Int,
                            @Field("total") total: Int,
                            @Field("status") status: String,
                            @Field("destinasi_id") destinationId: Int,
                            @Field("checkin") checkin:String,
                            @Field("nama_bandara")namaBandara:String,
                            @Field("provinsi") provinsi:String,
                            @Field("jam_terbang")jamTerbang: String,
                            @Field("picture_pesawat")picturePesawat:String): Observable<Iterable<CheckoutResponse>>

    @GET("transaction")
    fun transactionPending(@Query("status") status:String):Observable<Iterable<TransactionResponse>>

    @GET("explore")
    fun explore():Observable<ExploreResponse>
}