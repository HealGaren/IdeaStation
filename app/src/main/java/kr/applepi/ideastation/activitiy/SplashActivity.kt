package kr.applepi.ideastation.activitiy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import kr.applepi.ideastation.R
import kr.applepi.ideastation.data.AuthData
import kr.applepi.ideastation.data.IdeaData
import kr.applepi.ideastation.data.MyRetrofit
import kr.applepi.ideastation.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if(AuthData.serverToken == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else {
                MyRetrofit.retrofit.getMyInfo(AuthData.serverToken).enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>?, response: Response<User>) {
                        when(response.code()){
                            200 -> {
                                Toast.makeText(this@SplashActivity, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                                finish()
                            }
                            401 -> {
                                Toast.makeText(this@SplashActivity, "유효하지 않은 토큰입니다.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                                finish()
                            }
                            500 -> {
                                Toast.makeText(this@SplashActivity, "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                        }
                    }

                    override fun onFailure(call: Call<User>?, t: Throwable?) {
                        Toast.makeText(this@SplashActivity, "인터넷 상태를 확인해주세요.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                })
            }
        }, 3000)
    }
}
