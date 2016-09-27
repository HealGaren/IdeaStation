package kr.applepi.ideastation.activities

import android.content.Context
import android.databinding.DataBindingUtil
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_idea.*
import kotlinx.android.synthetic.main.dialog_review_select.*
import kotlinx.android.synthetic.main.dialog_review_select.view.*
import kotlinx.android.synthetic.main.dialog_review_swot.view.*
import kotlinx.android.synthetic.main.content_idea.*
import kr.applepi.ideastation.BR
import kr.applepi.ideastation.R
import kr.applepi.ideastation.data.*
import kr.applepi.ideastation.databinding.ActivityIdeaBinding
import java.security.acl.LastOwnerException
import java.util.*

class IdeaActivity : BaseAppCompatActivity() {

    var dialog: AlertDialog? = null

    val binding : ActivityIdeaBinding by lazy {
        DataBindingUtil.setContentView<ActivityIdeaBinding>(this, R.layout.activity_idea)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.setVariable(BR.item, Data.ideaList.get(intent.getIntExtra("position", 0)))
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collapsingToolbarLayout.setExpandedTitleColor(0x00ffffff)

        var select: View = btn_tab_detail
        arrayOf(btn_tab_detail, btn_tab_review).forEach {
            it.setOnClickListener {
                if (select == it) return@setOnClickListener
                select = it
                flipper_idea.showNext()
                when (it) {
                    btn_tab_detail -> {
                        btn_tab_detail.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        btn_tab_review.setTextColor(0xFFBDBDBD.toInt())
                        line_review.visibility = View.INVISIBLE
                        line_detail.visibility = View.VISIBLE
                        fab_add.hide()
                    }
                    else -> {
                        btn_tab_review.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        btn_tab_detail.setTextColor(0xFFBDBDBD.toInt())
                        line_detail.visibility = View.INVISIBLE
                        line_review.visibility = View.VISIBLE
                        fab_add.show()
                    }
                }

            }
        }

        fab_add.apply{ setOnClickListener {
            dialog = AlertDialog.Builder(this@IdeaActivity).setView(getReviewDialog()).show()
        } }.hide()
    }

    var selectType: View? = null


    fun isExistReview(id: Int): Boolean {
        return when (id) {
            R.id.btn_select_swot -> true
            R.id.btn_select_scamper -> true
            R.id.btn_select_5why -> false
            else/*R.id.btn_select_custom*/ -> true
        }
    }

    private fun getReviewDialog(): View {
        val outerThis = this
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_review_select, null)
        view.run {
            val arr = arrayOf(btn_select_swot, btn_select_scamper, btn_select_5why, btn_select_custom)
            arr.forEach {
                it.setOnClickListener {

                    if (!btn_select_done.isEnabled) btn_select_done.run {
                        alpha = 1f
                        isEnabled = true
                    }

                    if (it != selectType) {
                        text_select_error.run { if (visibility == View.VISIBLE) visibility = View.INVISIBLE }
                        selectType?.alpha = 0.6f
                        selectType = it
                        it.alpha = 1f
                    }

                    if (!isExistReview(it.id)) {
                        text_select_error.visibility = View.VISIBLE
                        if (btn_select_done.isEnabled) btn_select_done.run {
                            alpha = 0.6f
                            isEnabled = false
                        }
                    }
                }
            }

            btn_select_done.setOnClickListener {
                dialog!!.dismiss()
                dialog = AlertDialog.Builder(outerThis).setView(getSWOTDialog()).show()
            }
        }
        return view
    }


    private fun getSWOTDialog(): View {
        val outerThis = this
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_review_swot, null)
        view.run {
            btn_swot_done.setOnClickListener {

                var flag = false
                if (edit_strength.text.length == 0) {
                    edit_strength_layout.error = "강점을 입력해야 합니다."
                    flag = true
                }
                if (edit_weakness.text.length == 0) {
                    edit_weakness_layout.error = "약점을 입력해야 합니다."
                    flag = true
                }
                if (edit_opportunity.text.length == 0) {
                    edit_opportunity_layout.error = "기회를 입력해야 합니다."
                    flag = true
                }
                if (edit_threat.text.length == 0) {
                    edit_threat_layout.error = "위기를 입력해야 합니다."
                    flag = true
                }

                if (!flag) dialog!!.dismiss()
            }
        }
        return view
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
