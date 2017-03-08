package kr.applepi.ideastation.data

import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * Created by 최예찬 on 2016-09-30.
 */
object AuthData {


    private lateinit var context: Context
    private lateinit var pref: SharedPreferences

    var facebookToken: String? = null
    var facebookId: String? = null
    var serverToken: String? = null

    private const val SERVER_TOKEN_KEY: String = "server_token"
    fun init(context: Context){
        this.context = context
        pref = context.getSharedPreferences("AUTH", Context.MODE_PRIVATE)
        loadToken()
    }


    fun loadToken(){
        serverToken = pref.getString(SERVER_TOKEN_KEY, null)
    }

    fun saveToken(){
        pref.edit().putString(SERVER_TOKEN_KEY, serverToken).apply()
    }

}