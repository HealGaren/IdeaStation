package kr.applepi.ideastation.activitiy

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.sromku.simple.fb.Permission
import com.sromku.simple.fb.SimpleFacebook
import com.sromku.simple.fb.SimpleFacebookConfiguration
import com.sromku.simple.fb.entities.Profile
import com.sromku.simple.fb.listeners.OnLoginListener
import com.sromku.simple.fb.listeners.OnProfileListener
import kotlinx.android.synthetic.main.activity_login.*
import kr.applepi.ideastation.R
import kr.applepi.ideastation.data.AuthData
import kr.applepi.ideastation.data.MyRetrofit
import kr.applepi.ideastation.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by 최예찬 on 2016-09-30.
 */
class LoginActivity : BaseAppCompatActivity() {

    val TAG = "LoginActivity"

    var mSimpleFacebook: SimpleFacebook? = null
    var mProgressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mSimpleFacebook = SimpleFacebook.getInstance(this)

        val permissions: Array<Permission> = arrayOf(
                Permission.USER_PHOTOS, Permission.EMAIL, Permission.PUBLISH_ACTION,
                Permission.USER_ABOUT_ME, Permission.USER_FRIENDS, Permission.USER_BIRTHDAY
        )


        val configuration = SimpleFacebookConfiguration.Builder()
                .setAppId(getString(R.string.app_id))
                .setNamespace(getString(R.string.app_name))
                .setPermissions(permissions)
                .build()

        SimpleFacebook.setConfiguration(configuration)

        login_facebook.setOnClickListener {
            mProgressDialog = ProgressDialog.show(this, null, "로그인 시도 중입니다...", true)
            mSimpleFacebook?.login(onLoginListener)
        }

        no_login.setOnClickListener {
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mSimpleFacebook = SimpleFacebook.getInstance(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSimpleFacebook?.onActivityResult(requestCode, resultCode, data)
    }


    // 페이스북 로그인 Callback
    val onLoginListener: OnLoginListener = object : OnLoginListener {

        override fun onLogin(accessToken: String, acceptedPermissions: List<Permission>?, declinedPermissions: List<Permission>?) {
            // change the state of the button or do whatever you want
            AuthData.facebookToken = accessToken
            mSimpleFacebook?.getProfile(onProfileListener)
        }

        override fun onCancel() {
            // user canceled the dialog
            mProgressDialog?.dismiss()
            Toast.makeText(this@LoginActivity, "페이스북 로그인이 취소되었습니다", Toast.LENGTH_SHORT).show()
        }

        override fun onFail(reason: String) {
            // failed to login
            mProgressDialog?.dismiss()
            Log.d("onFail", reason)
            Toast.makeText(this@LoginActivity, "페이스북 로그인에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        }

        override fun onException(throwable: Throwable) {
            // exception from facebook
            mProgressDialog?.dismiss()
            Toast.makeText(this@LoginActivity, "일시적인 오류입니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
            Log.d(TAG, throwable.message)
        }
    }


    val onProfileListener = object : OnProfileListener() {
        override fun onComplete(profile: Profile) {
            Toast.makeText(this@LoginActivity, "안녕하세요, " + profile.name + "님!", Toast.LENGTH_SHORT).show()

            AuthData.facebookId = profile.id
            val retrofit = MyRetrofit.retrofit
            val call = retrofit.login(AuthData.facebookId, AuthData.facebookToken)
            call.enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    when(response.code()) {
                        200 -> {
                            AuthData.serverToken = response.body().token
                            AuthData.saveToken()

                            mProgressDialog?.dismiss()
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }
                        401 -> {
                            register()
                        }
                        500 -> {
                            mProgressDialog?.dismiss()
                            Toast.makeText(this@LoginActivity, "서버에서 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    mProgressDialog?.dismiss()
                    Log.d(TAG, "로그인 실패 : " + t.toString())
                }

            })
        }
    }

    fun register(){
        MyRetrofit.retrofit.register(AuthData.facebookId, AuthData.facebookToken).enqueue(object: Callback<User>{

            override fun onResponse(call: Call<User>, response: Response<User>) {
                when(response.code()) {
                    200 -> {
                        AuthData.serverToken = response.body().token
                        mProgressDialog?.dismiss()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                    409 -> {
                        mProgressDialog?.dismiss()
                        Toast.makeText(this@LoginActivity, "이미 가입하셨습니다.", Toast.LENGTH_SHORT).show()
                    }
                    500 -> {
                        mProgressDialog?.dismiss()
                        Toast.makeText(this@LoginActivity, "서버에서 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this@LoginActivity, "" + response.code(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                mProgressDialog?.dismiss()
                Log.d(TAG, "로그인 실패 : " + t.toString())
            }

        })
    }
}