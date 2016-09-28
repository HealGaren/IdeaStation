package kr.applepi.ideastation.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_write.view.*
import kr.applepi.ideastation.R
import kr.applepi.ideastation.activitiy.ListActivity
import kr.applepi.ideastation.data.Data
import kr.applepi.ideastation.data.Idea
import kr.applepi.ideastation.data.IdeaDetail
import kr.applepi.ideastation.data.IdeaReview

/**
 * Created by 최예찬 on 2016-09-28.
 */

class WriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater!!.inflate(R.layout.fragment_write, container, false)

        view.run {

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
                    activity.finish()
                    startActivity(Intent(context, ListActivity::class.java))
                }
            }
        }

        return view
    }
}