package kr.applepi.ideastation.activitiy

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kr.applepi.ideastation.R
import kr.applepi.ideastation.databinding.ActivitySearchBinding

class SearchActivity : BaseAppCompatActivity() {

    val binding : ActivitySearchBinding by lazy {
        DataBindingUtil.setContentView<ActivitySearchBinding>(this, R.layout.activity_search)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
