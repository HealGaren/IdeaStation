package kr.applepi.ideastation.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_mandarat.*
import kotlinx.android.synthetic.main.fragment_write.*
import kr.applepi.ideastation.R
import kr.applepi.ideastation.activitiy.ListActivity
import kr.applepi.ideastation.activitiy.MandaratActivity
import kr.applepi.ideastation.data.Data
import kr.applepi.ideastation.data.Idea
import kr.applepi.ideastation.data.IdeaDetail
import kr.applepi.ideastation.data.IdeaReview

/**
 * Created by 최예찬 on 2016-09-28.
 */

class MandaratFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {


        val view = inflater!!.inflate(R.layout.fragment_mandarat, container, false)


        return view
    }
}