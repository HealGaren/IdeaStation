package kr.applepi.ideastation.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_mandarat.*
import kotlinx.android.synthetic.main.fragment_mandarat.view.*
import kotlinx.android.synthetic.main.fragment_words.view.*
import kotlinx.android.synthetic.main.fragment_write.*
import kr.applepi.ideastation.R
import kr.applepi.ideastation.activitiy.ListActivity
import kr.applepi.ideastation.activitiy.MandaratActivity
import kr.applepi.ideastation.data.*
import java.util.*

/**
 * Created by 최예찬 on 2016-09-28.
 */

class WordsFragment : Fragment() {

    val lockBoolArr: Array<Boolean> = Array(3){false}
    lateinit var lockTextArr: Array<TextView>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {


        val view = inflater!!.inflate(R.layout.fragment_words, container, false)

        view.run{
            lockTextArr = arrayOf(word1, word2, word3)
            for(i in 0..lockTextArr.size-1){
                lockTextArr[i].setOnClickListener { toggleLock(i) }
            }
            btn_shuffle.setOnClickListener { shuffle() }
        }
        shuffle()
        return view
    }

    private fun toggleLock(index: Int){
        if(!lockBoolArr[index]){
            lockTextArr[index].run{
                setBackgroundResource(R.drawable.circle_maincolor_dark_bg_maicolor)
                setTextColor(0xffffffff.toInt())
            }
            lockBoolArr[index] = true
        }
        else {
            lockTextArr[index].run{
                setBackgroundResource(R.drawable.circle_maincolor_dark_bg_white)
                setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
            }
            lockBoolArr[index] = false
        }
    }

    private fun shuffle() {
        for (i in 0..lockTextArr.size - 1) {
            if(!lockBoolArr[i]) lockTextArr[i].text = DB.getRandWord()
        }
    }
}