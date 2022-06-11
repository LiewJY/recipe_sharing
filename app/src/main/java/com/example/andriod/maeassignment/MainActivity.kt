package com.example.andriod.maeassignment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.andriod.maeassignment.ui.app.AppActivity
import com.example.andriod.maeassignment.viewmodel.auth.LoginViewModel


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
        //setContentView(R.layout.activity_main)

        viewModel.isLogin()
        viewModel.isLogin.observe(this) { status ->
            if(status == true) {
                finish()
                val startApp = Intent(applicationContext, AppActivity::class.java)
                startActivity(startApp)
            } else {
                setContentView(R.layout.activity_main)
            }
        }
    }
}

