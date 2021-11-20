package com.mityushovn.imageviewerrestapigooglecourse.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mityushovn.imageviewerrestapigooglecourse.R
import com.mityushovn.imageviewerrestapigooglecourse.databinding.ActivityMainBinding
import com.mityushovn.imageviewerrestapigooglecourse.overview.OverviewFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        if (savedInstanceState == null) {
            val fragment = OverviewFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.main_activity_fragment_container, fragment)
                .commit()
        }
    }
}