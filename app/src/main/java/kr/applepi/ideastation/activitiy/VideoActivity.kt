package kr.applepi.ideastation.activitiy

import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.item_video2.*
import kr.applepi.ideastation.R
import kr.applepi.ideastation.databinding.ActivitySearchBinding

class VideoActivity : BaseAppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collapsingToolbarLayout.setExpandedTitleColor(0x00ffffff);

        val frameVideo = "<html><body><iframe width=\"320\" height=\"180\" src=\"https://www.youtube.com/embed/2buKYjpqcVQ\" frameborder=\"0\" allowfullscreen></iframe></body></html>"

        webview.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        })
        val webSettings = webview.getSettings()
        webSettings.javaScriptEnabled = true
        webview.loadData(frameVideo, "text/html", "utf-8")

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }


    fun stopMediaPlayer() {
        val player = MediaPlayer()
        if (player.isPlaying) {
            player.stop()
        }
    }

    override fun onPause() {
        super.onPause()
        webview.onPause()
    }

    override fun onResume() {
        super.onResume()
        webview.onResume()

    }
}
