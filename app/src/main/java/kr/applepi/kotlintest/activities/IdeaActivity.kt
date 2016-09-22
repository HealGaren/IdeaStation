package kr.applepi.kotlintest.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_idea.*
import kotlinx.android.synthetic.main.content_idea.*
import kr.applepi.kotlintest.R

class IdeaActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_idea)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        collapsingToolbarLayout.setExpandedTitleColor(0x00ffffff)


        var select: View = btn_tab_detail
        arrayOf(btn_tab_detail, btn_tab_review).forEach {
            it.setOnClickListener {
                if(select == it) return@setOnClickListener
                select = it
                flipper_idea.showNext()
                when (it) {
                    btn_tab_detail -> {
                        btn_tab_detail.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        btn_tab_review.setTextColor(0xFFBDBDBD.toInt())
                        line_review.visibility = View.INVISIBLE
                        line_detail.visibility = View.VISIBLE
                    }
                    else -> {
                        btn_tab_review.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        btn_tab_detail.setTextColor(0xFFBDBDBD.toInt())
                        line_detail.visibility = View.INVISIBLE
                        line_review.visibility = View.VISIBLE
                    }
                }

            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
