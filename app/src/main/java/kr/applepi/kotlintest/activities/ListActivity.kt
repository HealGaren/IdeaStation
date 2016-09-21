package kr.applepi.kotlintest.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_list.*
import kr.applepi.kotlintest.R

class ListActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)


        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collapsingToolbarLayout.setExpandedTitleColor(0x00ffffff);

    }
}
