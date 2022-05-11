package com.example.andriod.maeassignment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_auth)
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
