package kr.applepi.kotlintest

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arrayOf(btn_main, btn_left, btn_right, btn_third, btn_fourth).forEach { v -> v.setOnClickListener({
            startActivity(Intent(this, when (v) {
                btn_main -> IdeaActivity::class
                btn_left -> MandaratActivity::class
                btn_right -> VideoActivity::class
                btn_third -> ListActivity::class
                else -> SearchActivity::class
            }.java))
        }) }
    }

}
