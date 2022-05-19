package com.example.andriod.maeassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.andriod.maeassignment.ui.app.AppActivity
import com.example.andriod.maeassignment.ui.auth.AuthActivity
import com.example.andriod.maeassignment.viewmodel.LoginViewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //forced light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        FirebaseApp.initializeApp(applicationContext)

        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        viewModel.isLogin()
        viewModel.isLogin.observe(this) { status ->
            if(status == true) {
                val startApp = Intent(applicationContext, AppActivity::class.java)
                startActivity(startApp)
            //Log.e("frag", "home")
            } else {
                //supportActionBar?.hide()
                val startAuth = Intent(applicationContext, AuthActivity::class.java)
                startActivity(startAuth)
                // Log.e("frag", "auth")
            }
        }


        //hide action bar

//        val handler = Handler()
//        handler.postDelayed(setContentView(R.layout.activity_auth) ,5000)

        //startNewActivity(AuthActivity::class.java)

//        userPreferences.accessToken.asLiveData().observe(this, Observer {
//            val activity = if (it == null) AuthActivity::class.java else HomeActivity::class.java
//            startNewActivity(activity)
//        })
    }

}

//private fun Handler.postDelayed(contentView: Unit, l: Long) {
//
//}
