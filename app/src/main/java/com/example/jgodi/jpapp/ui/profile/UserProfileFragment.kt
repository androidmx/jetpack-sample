package com.example.jgodi.jpapp.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.example.jgodi.jpapp.R
import com.example.jgodi.jpapp.data.remote.UserModel
import kotlinx.android.synthetic.main.user_profile_layout.*

class UserProfileFragment : Fragment() {
    companion object {
        const val UID_KEY:String = "uid"
        fun newInstance() = UserProfileFragment()
    }

    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.user_profile_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel()::class.java)
        val userId = arguments?.getInt(UID_KEY) ?: 1
        viewModel.init(userId)

        viewModel.singleUser?.observe(this, Observer { singleUser: UserModel.SingleUser? ->
            // update UI
//            if(singleUser != null) {
//                val profile = singleUser.data.firstName + " " + singleUser.data.lastName
//                text_view_profile.text = profile
//            } else {
//                text_view_profile.text = "No se encontró información del usuario"
//            }
        })
    }

}
