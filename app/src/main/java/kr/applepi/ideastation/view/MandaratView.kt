package kr.applepi.ideastation.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.SharedPreferences
import android.content.res.TypedArray
import android.graphics.Canvas
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.view.animation.TranslateAnimation
import android.widget.*
import kotlinx.android.synthetic.main.tile_mandarat.view.*
import kotlinx.android.synthetic.main.tile_mandarat_show.view.*
import kotlinx.android.synthetic.main.view_mandarat.view.*
import kr.applepi.ideastation.R

/**
 * Created by 최예찬 on 2016-09-12.
 */


class MandaratView : RelativeLayout {

    companion object {
        const val PREF_KEY: String = "mandarat"
    }

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?)
    : this(context, attrs, 0)

    private val pref: SharedPreferences

    private var showAllMode: Boolean = false
        set(value) {
            if (!showAllMode && value) {

            } else if (showAllMode && !value) {

            }
        }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
    : super(context, attrs, defStyleAttr) {
        val typedArray: TypedArray = context!!.obtainStyledAttributes(attrs, R.styleable.MandaratView, defStyleAttr, 0)

        val textColor = typedArray.getColor(R.styleable.MandaratView_mandarat_textColor, 0)

        typedArray.recycle()

        pref = context.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)

        LayoutInflater.from(context).inflate(R.layout.view_mandarat, this)

        setTextColor(textColor)

        superView_edit.run {
            val tileArr = arrayOf(tile1, tile2, tile3, tile4, tile6, tile7, tile8, tile9)

            for (i in 0..tileArr.size - 1) {
                tileArr[i].run {

                    arrayOf(edit1, edit2, edit3, edit4, edit6, edit7, edit8, edit9).forEach {
                        it.setBackgroundColor(ContextCompat.getColor(context, R.color.colorMandaratLow))
                    }
                    edit5.setBackgroundColor(ContextCompat.getColor(context, R.color.colorMandaratMedium))

                    val arr: Array<Button> = arrayOf(
                            btn_right_down, btn_down, btn_left_down,
                            btn_right, btn_left,
                            btn_right_up, btn_up, btn_left_up
                    )

                    for (j in 0..arr.size - 1) {
                        if (i != j) arr[j].visibility = View.INVISIBLE
                        arr[j].setOnClickListener {
                            gotoParent()
                        }
                    }
                }
            }

            tile5.run {

                val editArr = arrayOf(edit1, edit2, edit3, edit4, edit6, edit7, edit8, edit9)
                for (i in 0..tileArr.size - 1) {
                    editArr[i].setBackgroundColor(ContextCompat.getColor(context, R.color.colorMandaratMedium))
                    editArr[i].addTextChangedListener(object : TextWatcher {
                        override fun afterTextChanged(p0: Editable?) {
                        }

                        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        }

                        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                            tileArr[i].edit5.setText(p0)
                        }
                    })

                }
                edit5.setBackgroundColor(ContextCompat.getColor(context, R.color.colorMandaratHigh))


                val btnArr = arrayOf(btn_left_up, btn_up, btn_right_up,
                        btn_left, btn_right,
                        btn_left_down, btn_down, btn_right_down
                )
                for (i in 0..btnArr.size - 1) {
                    btnArr[i].setOnClickListener { gotoChild(tileArr[i]) }
                }
            }
            setTileToCenter()
        }

        val edits = superView_edit.run { arrayOf(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9) }
        val texts = superView_show.run { arrayOf(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9) }

        for(i in 0..edits.size - 1){
            val editTexts = edits[i].run{ arrayOf(edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8, edit9) }
            val textViews = texts[i].run{ arrayOf(text1, text2, text3, text4, text5, text6, text7, text8, text9) }
            for(j in 0..editTexts.size - 1){
                editTexts[j].addTextChangedListener(object : TextWatcher{
                    override fun afterTextChanged(p0: Editable?) {
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        textViews[j].text = p0
                    }
                })
            }
        }

    }

    private fun gotoParent() {

        superView_edit.run {
            val ani = object : Animation() {

                val params: FrameLayout.LayoutParams = layoutParams as FrameLayout.LayoutParams
                val startLeft = params.leftMargin
                val targetLeftBy = -(tile4.width + params.leftMargin)
                val startTop = params.topMargin
                val targetTopBy = -(tile2.height + params.topMargin)

                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    params.leftMargin = startLeft + (targetLeftBy * interpolatedTime).toInt()
                    params.topMargin = startTop + (targetTopBy * interpolatedTime).toInt()
                    layoutParams = params
                }
            }
//        ani.isFillEnabled = true // 애니메이션 후 이동한좌표에
            ani.duration = 500 //지속시간
//        ani.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationRepeat(animation: Animation?) {
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                params.leftMargin = -tile4.width
//                params.topMargin = -tile2.height
//                superView.layoutParams = params
//            }
//
//            override fun onAnimationStart(animation: Animation?) {
//            }
//        })

            startAnimation(ani)
        }
    }


    private fun gotoChild(view: View) {

        superView_edit.run {

            val w: Float = when (view) {
                tile1, tile4, tile7 -> 0
                tile2, tile5, tile8 -> tile4.width
                else -> tile4.width + tile5.width
            }.toFloat()

            val h: Float = when (view) {
                tile1, tile2, tile3 -> 0
                tile4, tile5, tile6 -> tile2.height
                else -> tile2.height + tile5.height
            }.toFloat()

            val ani = object : Animation() {

                val params: FrameLayout.LayoutParams = layoutParams as FrameLayout.LayoutParams
                val startLeft = params.leftMargin
                val targetLeftBy = -(w + params.leftMargin)
                val startTop = params.topMargin
                val targetTopBy = -(h + params.topMargin)

                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    params.leftMargin = startLeft + (targetLeftBy * interpolatedTime).toInt()
                    params.topMargin = startTop + (targetTopBy * interpolatedTime).toInt()
                    layoutParams = params
                }
            }

//        ani.isFillEnabled = true // 애니메이션 후 이동한좌표에
            ani.duration = 500 //지속시간
//        ani.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationRepeat(animation: Animation?) {
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                params.leftMargin = -w.toInt()
//                params.topMargin = -h.toInt()
//                superView.layoutParams = params
//            }
//
//            override fun onAnimationStart(animation: Animation?) {
//            }
//
//        })


            startAnimation(ani)

        }
    }

    fun save() {
        arrayOf(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9).forEach {
            it.run {
                arrayOf(edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8, edit9).forEach {


                }
            }
        }
    }

    fun setTextColor(color: Int) {
        superView_edit.run {
            arrayOf(tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9).forEach {
                it.run {
                    arrayOf(edit1, edit2, edit3, edit4, edit5, edit6, edit7, edit8, edit9).forEach {
                        it.setTextColor(color)
                    }
                }
            }
        }
    }

    fun setTileToCenter() {
        superView_edit.run {
            val params = layoutParams as FrameLayout.LayoutParams
            params.leftMargin = -tile4.width
            params.topMargin = -tile2.height
            layoutParams = params
        }
    }
}