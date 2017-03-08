package kr.applepi.ideastation.activitiy

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
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
import kotlinx.android.synthetic.main.dialog_review_write_swot.view.*
import kotlinx.android.synthetic.main.content_idea.*
import kotlinx.android.synthetic.main.content_idea_review_5why.*
import kotlinx.android.synthetic.main.content_idea_review_custom.*
import kotlinx.android.synthetic.main.content_idea_review_swot.*
import kotlinx.android.synthetic.main.dialog_review_write.view.*
import kotlinx.android.synthetic.main.dialog_review_write_5why.view.*
import kotlinx.android.synthetic.main.dialog_review_write_custom.view.*
import kr.applepi.ideastation.BR
import kr.applepi.ideastation.R
import kr.applepi.ideastation.data.*
import kr.applepi.ideastation.databinding.ActivityIdeaBinding
import kr.applepi.ideastation.databinding.DialogReviewShow5whyBinding
import kr.applepi.ideastation.databinding.DialogReviewShowCustomBinding
import kr.applepi.ideastation.databinding.DialogReviewShowSwotBinding
import java.security.acl.LastOwnerException
import java.util.*

class IdeaActivity : BaseAppCompatActivity() {

    var dialog: AlertDialog? = null
    lateinit var idea: Idea

    val binding : ActivityIdeaBinding by lazy {
        DataBindingUtil.setContentView<ActivityIdeaBinding>(this, R.layout.activity_idea)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idea = IdeaData.ideaList.get(intent.getIntExtra("position", 0))
        binding.setVariable(BR.item, idea)
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

        arrayOf(card_review_custom, card_review_swot, card_review_5why).forEach{
            it.setOnClickListener{
                dialog = AlertDialog.Builder(this@IdeaActivity).setView(when(it){
                    card_review_custom -> getShowCustomDialog()
                    card_review_swot -> getShowSWOTDialog()
                    else -> getShow5whyDialog()
                }).show()
            }
        }
    }

    var selectType: View? = null


    fun isExistReview(id: Int): Boolean {
        return when (id) {
            R.id.btn_select_custom -> idea.review.reviewCustom
            R.id.btn_select_swot -> idea.review.reviewSWOT
            else /*R.id.btn_select_5why*/ -> idea.review.review5why
        } == null
    }

    private fun getReviewDialog(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_review_write, null)
        view.run {
            val arr = arrayOf(btn_select_custom, btn_select_swot, btn_select_5why)
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
                dialog = AlertDialog.Builder(this@IdeaActivity).setView(when(selectType) {
                    btn_select_custom -> getWriteCustomDialog()
                    btn_select_swot -> getWriteSWOTDialog()
                    else -> getWrite5whyDialog()
                }).show()
            }

            btn_search_patent.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://www.kipris.or.kr/khome/main.jsp")))
            }
        }
        return view
    }


    private fun getWrite5whyDialog(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_review_write_5why, null)
        view.run {
            btn_5why_done.setOnClickListener {

                var flag = false
                if (edit_problem.text.length == 0) {
                    edit_problem_layout.error = "문제점을 입력해야 합니다."
                    flag = true
                }
                if (edit_why1.text.length == 0) {
                    edit_why1_layout.error = "이유 1을 입력해야 합니다."
                    flag = true
                }
                if (edit_why2.text.length == 0) {
                    edit_why2_layout.error = "이유 1을 입력해야 합니다."
                    flag = true
                }
                if (edit_why3.text.length == 0) {
                    edit_why3_layout.error = "이유 1을 입력해야 합니다."
                    flag = true
                }
                if (edit_why4.text.length == 0) {
                    edit_why4_layout.error = "이유 1을 입력해야 합니다."
                    flag = true
                }
                if (edit_why5.text.length == 0) {
                    edit_why5_layout.error = "이유 1을 입력해야 합니다."
                    flag = true
                }
                if (edit_solution.text.length == 0) {
                    edit_solution_layout.error = "소모성을 입력해야 합니다."
                    flag = true
                }

                if (!flag) {
                    idea.review.review5why = IdeaReview5why(
                            edit_problem.text.toString(),
                            edit_why1.text.toString(),
                            edit_why2.text.toString(),
                            edit_why3.text.toString(),
                            edit_why4.text.toString(),
                            edit_why5.text.toString(),
                            edit_solution.text.toString()
                    )
                    IdeaData.saveIdeaList()
                    binding.setVariable(BR.item, idea)
                    dialog!!.dismiss()
                }
            }
        }
        return view
    }

    private fun getWriteCustomDialog(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_review_write_custom, null)
        view.run {
            btn_custom_done.setOnClickListener {

                var flag = false
                if (edit_popularity.text.length == 0) {
                    edit_popularity_layout.error = "대중성을 입력해야 합니다."
                    flag = true
                }
                if (edit_essentiality.text.length == 0) {
                    edit_essentiality_layout.error = "필수성을 입력해야 합니다."
                    flag = true
                }
                if (edit_expendable.text.length == 0) {
                    edit_expendable_layout.error = "소모성을 입력해야 합니다."
                    flag = true
                }
                if (edit_convenience.text.length == 0) {
                    edit_convenience_layout.error = "편리성을 입력해야 합니다."
                    flag = true
                }

                if (!flag) {
                    idea.review.reviewCustom = IdeaReviewCustom(
                            edit_popularity.text.toString(), edit_essentiality.text.toString(),
                            edit_expendable.text.toString(), edit_convenience.text.toString()
                    )
                    IdeaData.saveIdeaList()
                    binding.setVariable(BR.item, idea)
                    dialog!!.dismiss()
                }
            }
        }
        return view
    }

    private fun getWriteSWOTDialog(): View {
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_review_write_swot, null)
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

                if (!flag) {
                    idea.review.reviewSWOT = IdeaReviewSWOT(
                            edit_strength.text.toString(), edit_weakness.text.toString(),
                            edit_opportunity.text.toString(), edit_threat.text.toString()
                    )
                    IdeaData.saveIdeaList()
                    binding.setVariable(BR.item, idea)
                    dialog!!.dismiss()
                }
            }
        }
        return view
    }



    private fun getShow5whyDialog(): View {
        val binding = DataBindingUtil.inflate<DialogReviewShow5whyBinding>(layoutInflater, R.layout.dialog_review_show_5why, null, false)
        binding.setVariable(BR.item, idea.review.review5why)
        return binding.root
    }

    private fun getShowCustomDialog(): View {
        val binding = DataBindingUtil.inflate<DialogReviewShowCustomBinding>(layoutInflater, R.layout.dialog_review_show_custom, null, false)
        binding.setVariable(BR.item, idea.review.reviewCustom)
        return binding.root
    }

    private fun getShowSWOTDialog(): View {
        val binding = DataBindingUtil.inflate<DialogReviewShowSwotBinding>(layoutInflater, R.layout.dialog_review_show_swot, null, false)
        binding.setVariable(BR.item, idea.review.reviewSWOT)
        return binding.root
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
