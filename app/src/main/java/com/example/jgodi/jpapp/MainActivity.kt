package com.example.jgodi.jpapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jgodi.jpapp.ui.user.ListUsersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ListUsersFragment.newInstance())
                    .commitNow()
        }
    }

}
