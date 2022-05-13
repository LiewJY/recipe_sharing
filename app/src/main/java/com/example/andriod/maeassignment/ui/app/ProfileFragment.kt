package com.example.andriod.maeassignment.ui.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.andriod.maeassignment.R
import com.example.andriod.maeassignment.databinding.FragmentProfileBinding
import com.example.andriod.maeassignment.ui.auth.AuthActivity
import com.example.andriod.maeassignment.viewmodel.LogoutViewModel

class ProfileFragment : Fragment(),  View.OnClickListener{

    private val viewModel: LogoutViewModel by lazy {
        ViewModelProvider(this).get(LogoutViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater,
            R.layout.fragment_profile,container,false)

        //register event handler (for the button)
        binding.btnLogout.setOnClickListener(this)


        return binding.root
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btnLogout -> {
                    viewModel.logout()
                    viewModel.logoutStatus.observe(this) { result ->
                        if(result == true) {
                            Toast.makeText(context, "Logout Success", Toast.LENGTH_SHORT).show()
                        }
                    }
                    //go to auth
                    val intent = Intent(activity, AuthActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

}