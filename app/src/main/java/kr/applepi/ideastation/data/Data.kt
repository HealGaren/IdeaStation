package kr.applepi.ideastation.data

import android.content.Context
import android.content.SharedPreferences
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * Created by 최예찬 on 2016-09-24.
 */
object Data {

    lateinit var context: Context
    lateinit var pref: SharedPreferences

    val ideaList: ArrayList<Idea> = ArrayList()
    val gson = Gson()
    val type: Type = object : TypeToken<ArrayList<Idea>>(){}.type

    const val IDEA_LIST_KEY: String = "idea_list"
    fun init(context: Context){
        this.context = context
        pref = context.getSharedPreferences("IDEA", Context.MODE_PRIVATE)
        loadIdeaList()
    }

    fun loadIdeaList(){
        ideaList.clear()
        ideaList.addAll(gson.fromJson<ArrayList<Idea>>(pref.getString(IDEA_LIST_KEY, "[]"), type))
    }

    fun saveIdeaList(){
        pref.edit().putString(IDEA_LIST_KEY, gson.toJson(ideaList)).apply()
    }
}