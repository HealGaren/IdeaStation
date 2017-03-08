package kr.applepi.ideastation.data

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Created by 최예찬 on 2016-09-30.
 */
object MyRetrofit {
    val retrofit = Retrofit.Builder().baseUrl("http://idea.applepi.kr").addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create())).build().create(MyService::class.java)
}


interface MyService {
    @FormUrlEncoded
    @POST("users/register-facebook")
    fun register(@Field("id") id: String?, @Field("token") token: String?): Call<User>

    @FormUrlEncoded
    @POST("users/login-facebook")
    fun login(@Field("id") id: String?, @Field("token") token: String?): Call<User>

    @FormUrlEncoded
    @POST("users/me/idea")
    fun postIdea(@Header("Login-Token") token: String?, @Field("idea") idea: String?): Call<String>

    @GET("users/me")
    fun getMyInfo(@Header("Login-Token") token: String?): Call<User>

    @GET("users/me/idea")
    fun getIdea(@Header("Login-Token") token: String?): Call<String>
}

data class User(val token: String)