package kr.applepi.kotlintest.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.activity_select.view.*
import kr.applepi.kotlintest.R
import kr.applepi.kotlintest.animations.FlipAnimation

/**
 * Created by 최예찬 on 2016-09-18.
 */
class SelectActivity : BaseAppCompatActivity() {

    var selectType: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)
        setSupportActionBar(toolbar)
        arrayOf(btn_select_mandarat, btn_select_words).forEach { it.setOnClickListener{

            if(!btn_select_done.isEnabled) btn_select_done.run{
                alpha = 1f
                isEnabled = true
            }

            if(it != selectType) {
                when (it) {
                    btn_select_mandarat -> {
                        it.alpha = 1f
                        btn_select_words.alpha = 0.6f
                    }
                    else -> {
                        it.alpha = 1f
                        btn_select_mandarat.alpha = 0.6f
                    }
                }
                selectType = it
            }
            else flipCard(it.flipView)

        }}

        btn_select_done.setOnClickListener{
            finish();
            startActivity(Intent(this, when(selectType){
                btn_select_mandarat -> MandaratActivity::class
                else -> MandaratActivity::class
            }.java))
        }
    }

    private fun flipCard(view: View) {

        val front = view.findViewById(R.id.cardView_front)
        val back = view.findViewById(R.id.cardView_back)

        val flipAnimation = FlipAnimation(front, back)

        if (front.visibility === View.GONE) {
            flipAnimation.reverse()
        }
        view.startAnimation(flipAnimation)
    }
}