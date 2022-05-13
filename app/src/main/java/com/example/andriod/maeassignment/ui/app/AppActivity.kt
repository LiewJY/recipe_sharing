package com.example.andriod.maeassignment.ui.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.ActivityAppBinding
import com.google.android.material.bottomnavigation.BottomNavigationView



class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.appBottomNav

        val navController = findNavController(R.id.app_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_add, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}

//        val navController = Navigation.findNavController(this, R.id.app_host_fragment)
//        val bottomNavigationView =
//            findViewById<BottomNavigationView>(R.id.app_bottom_nav)
//        setupWithNavController(bottomNavigationView, navController)

//        //Initialize Bottom Navigation View.
//        //Initialize Bottom Navigation View.
//        val navView = findViewById<BottomNavigationView>(R.id.bottomNav_view)
//
//        //Pass the ID's of Different destinations
//
//        //Pass the ID's of Different destinations
//        val appBarConfiguration: AppBarConfiguration = Builder(
//            R.id.navigation_home,
//            R.id.navigation_add,
//            R.id.navigation_profile,
//        )
//            .build()
//
//        //Initialize NavController.
//
//        //Initialize NavController.
//        val navController: NavController = Navigation.findNavController(this, R.id.navHostFragment)
//        setupActionBarWithNavController(this, navController, appBarConfiguration)
//        setupWithNavController(navView, navController)
