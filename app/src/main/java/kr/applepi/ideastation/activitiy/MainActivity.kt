package kr.applepi.ideastation.activitiy

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kr.applepi.ideastation.R
import kr.applepi.ideastation.data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
//        val fab = findViewById(R.id.fab) as FloatingActionButton?
//        fab!!.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() }

        arrayOf(btn_selectIdea, btn_directIdea, btn_ideaList, btn_ideaVideo).forEach { it.setOnClickListener({
            startActivity(Intent(this, when (it) {
                btn_selectIdea -> SelectActivity::class
                btn_directIdea -> DirectActivity::class
                btn_ideaList -> ListActivity::class
                else/*btn_ideaVideo*/ -> VideoActivity::class
            }.java))
        }) }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)

        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    var mProgressDialog: ProgressDialog? = null

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        if(AuthData.serverToken == null){
            Toast.makeText(this@MainActivity, "로그인하지 않으셨습니다. 앱 재접속 후 로그인해주세요.", Toast.LENGTH_SHORT).show()
            drawer_layout.closeDrawer(GravityCompat.START)
            return true
        }
        when(item.itemId){
            R.id.nav_btn_upload -> {
                mProgressDialog = ProgressDialog.show(this, null, "서버와 통신 중입니다...", true)
                MyRetrofit.retrofit.postIdea(AuthData.serverToken, IdeaData.getDataString()).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>?, response: Response<String>) {
                        when(response.code()){
                            200 -> {
                                Toast.makeText(this@MainActivity, "백업이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                mProgressDialog?.dismiss()
                            }
                            500 -> {
                                Toast.makeText(this@MainActivity, "서버 오류가 발생했습니다..", Toast.LENGTH_SHORT).show()
                                mProgressDialog?.dismiss()
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        t?.printStackTrace()
                        Toast.makeText(this@MainActivity, "인터넷 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        mProgressDialog?.dismiss()
                    }
                })
            }
            R.id.nav_btn_download -> {
                mProgressDialog = ProgressDialog.show(this, null, "서버와 통신 중입니다...", true)
                MyRetrofit.retrofit.getIdea(AuthData.serverToken).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>?, response: Response<String>) {
                        when(response.code()){
                            200 -> {
                                Toast.makeText(this@MainActivity, "복원이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                IdeaData.applyDataString(response.body())
                                IdeaData.saveIdeaList()
                                mProgressDialog?.dismiss()
                            }
                            500 -> {
                                Toast.makeText(this@MainActivity, "서버 오류가 발생했습니다..", Toast.LENGTH_SHORT).show()
                                mProgressDialog?.dismiss()
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>?, t: Throwable?) {
                        t?.printStackTrace()
                        Toast.makeText(this@MainActivity, "인터넷 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        mProgressDialog?.dismiss()
                    }
                })
            }
            else->{}
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
