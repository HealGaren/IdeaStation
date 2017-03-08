package kr.applepi.ideastation.activitiy

import android.databinding.DataBindingUtil
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_video.*
import kr.applepi.ideastation.R
import kr.applepi.ideastation.databinding.ActivitySearchBinding

class VideoActivity : BaseAppCompatActivity() {

    val webviewArr: Array<Pair<WebView, String>?> = Array(4){null}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        collapsingToolbarLayout.setExpandedTitleColor(0x00ffffff);

        webviewArr[0] = Pair(video1.findViewById(R.id.webview) as WebView, "https://www.youtube.com/embed/qJYn1rCQajQ")
        webviewArr[1] = Pair(video2.findViewById(R.id.webview) as WebView, "https://www.youtube.com/embed/2buKYjpqcVQ")
        webviewArr[2] = Pair(video3.findViewById(R.id.webview) as WebView, "https://www.youtube.com/embed/p7UEmB3QkvE")
        webviewArr[3] = Pair(video4.findViewById(R.id.webview) as WebView, "https://www.youtube.com/embed/BsN4RU4QaTQ")

        loadVideo()

    }

    fun loadVideo(){
        webviewArr.forEach {
            if(it == null) return@forEach
            val frameVideo = "<html><body><iframe width=\"320\" height=\"180\" src=\"" + it.second + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>"

            it.first.setWebViewClient(object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    return false
                }
            })
            val webSettings = it.first.getSettings()
            webSettings.javaScriptEnabled = true
            it.first.loadData(frameVideo, "text/html", "utf-8")
        }
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
        webviewArr.forEach{ it?.first?.onPause() }
    }

    override fun onResume() {
        super.onResume()
        webviewArr.forEach{ it?.first?.onResume() }

    }
}
