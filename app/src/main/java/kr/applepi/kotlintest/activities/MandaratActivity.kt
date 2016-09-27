package kr.applepi.kotlintest.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import kotlinx.android.synthetic.main.activity_mandarat.*
import kotlinx.android.synthetic.main.content_mandarat_write_input.view.*
import kotlinx.android.synthetic.main.dialog_mandarat_write.*
import kotlinx.android.synthetic.main.dialog_mandarat_write.view.*
import kr.applepi.kotlintest.R
import kr.applepi.kotlintest.data.*
import kr.applepi.kotlintest.databinding.ActivityListBinding

class MandaratActivity : BaseAppCompatActivity() {

    var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mandarat)
        setSupportActionBar(toolbar)
        btn_write.setOnClickListener {
            dialog = AlertDialog.Builder(this).setView(getWriteDialog()).show()
        }

    }

    private fun getWriteDialog(): View {
        val outerThis = this
        val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_mandarat_write, null)
        view.run {


            var select: View = btn_tab_input
            arrayOf(btn_tab_input, btn_tab_triz).forEach {
                it.setOnClickListener {
                    if (select == it) return@setOnClickListener
                    select = it
                    flipper_mandarat.showNext()
                    when (it) {
                        btn_tab_input -> {
                            btn_tab_input.setTextColor(ContextCompat.getColor(this@MandaratActivity, R.color.colorPrimary))
                            btn_tab_triz.setTextColor(0xFFBDBDBD.toInt())
                            line_review.visibility = View.INVISIBLE
                            line_detail.visibility = View.VISIBLE
                        }
                        else -> {
                            btn_tab_triz.setTextColor(ContextCompat.getColor(this@MandaratActivity, R.color.colorPrimary))
                            btn_tab_input.setTextColor(0xFFBDBDBD.toInt())
                            line_detail.visibility = View.INVISIBLE
                            line_review.visibility = View.VISIBLE
                        }
                    }

                }
            }

            btn_input_done.setOnClickListener {

                var flag = false
                if (edit_title.text.length == 0) {
                    edit_title_layout.error = "제목을 입력해야 합니다."
                    flag = true
                }
                if (edit_content.text.length == 0) {
                    edit_content_layout.error = "내용을 입력해야 합니다."
                    flag = true
                }
                if (edit_benefit.text.length == 0) {
                    edit_benefit_layout.error = "이점을 입력해야 합니다."
                    flag = true
                }

                if (!flag) {

                    Data.ideaList.add(
                            Idea(
                                    IdeaDetail(edit_title.text.toString(), edit_content.text.toString(), edit_benefit.text.toString(),
                                            edit_what.text.toString(), edit_who.text.toString(), edit_why.text.toString(), edit_when.text.toString(), edit_where.text.toString()
                                    ),
                                    IdeaReview(null)
                                    )
                            )
                    Data.saveIdeaList()

                    dialog!!.dismiss()
                    finish()
                    startActivity(Intent(this@MandaratActivity, ListActivity::class.java))
                }
            }
        }
        return view
    }

}
