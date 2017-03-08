package kr.applepi.ideastation.activitiy

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_mandarat.*
import kr.applepi.ideastation.R

class MandaratActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mandarat)
        setSupportActionBar(toolbar)
        initFragment()
    }

    private fun initFragment() {
        var select: View = btn_tab_makeIdea
        arrayOf(btn_tab_makeIdea, btn_tab_write).forEach {
            it.setOnClickListener {
                if (select == it) return@setOnClickListener
                flipper_mandarat.showNext()
                select = it
                when (it) {
                    btn_tab_makeIdea -> {
                        btn_tab_makeIdea.setTextColor(ContextCompat.getColor(this@MandaratActivity, R.color.colorPrimary))
                        btn_tab_write.setTextColor(0xFFBDBDBD.toInt())
                        line_review.visibility = View.INVISIBLE
                        line_detail.visibility = View.VISIBLE
                    }
                    else -> {
                        btn_tab_write.setTextColor(ContextCompat.getColor(this@MandaratActivity, R.color.colorPrimary))
                        btn_tab_makeIdea.setTextColor(0xFFBDBDBD.toInt())
                        line_detail.visibility = View.INVISIBLE
                        line_review.visibility = View.VISIBLE
                    }
                }
            }
        }

    }

}
