package kr.applepi.ideastation.activitiy

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.tsengvn.typekit.TypekitContextWrapper

/**
 * Created by User7 on 2016-09-21.
 */
open class BaseAppCompatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase))
    }

    fun replaceCotentFragment(@IdRes frameId: Int, fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(frameId, fragment).commit()
    }
}
