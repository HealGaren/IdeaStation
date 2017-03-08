package kr.applepi.ideastation.activitiy

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_mandarat.*
import kr.applepi.ideastation.R

class DirectActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direct)
        setSupportActionBar(toolbar)
        initFragment()
    }

    private fun initFragment() {

    }

}
