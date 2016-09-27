package kr.applepi.kotlintest

import android.app.Application
import com.tsengvn.typekit.Typekit
import kr.applepi.kotlintest.data.Data
import kr.applepi.kotlintest.data.IdeaDetail

/**
 * Created by 최예찬 on 2016-09-13.
 */
class PiApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Typekit.getInstance()
                .addCustom1(Typekit.createFromAsset(this, "NanumBarunGothicLight.otf"))
                .addNormal(Typekit.createFromAsset(this, "NanumBarunGothic.otf"))
                .addBold(Typekit.createFromAsset(this, "NanumBarunGothicBold.otf"))
        Data.init(this)
    }

}