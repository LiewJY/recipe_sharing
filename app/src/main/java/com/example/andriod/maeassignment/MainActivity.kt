package com.example.andriod.maeassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //forced light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        setContentView(R.layout.activity_main)
        //        handler.postDelayed(setContentView(R.layout.activity_auth) ,5000)
        supportActionBar?.hide()


        setContentView(R.layout.activity_auth)


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
