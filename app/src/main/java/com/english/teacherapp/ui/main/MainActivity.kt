package com.english.teacherapp.ui.main

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.english.teacherapp.R
import com.english.teacherapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sectionPagerAdapter: MainPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.WHITE

        binding.navBottomView.setOnNavigationItemSelectedListener(this)
        sectionPagerAdapter = MainPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.viewPager.isUserInputEnabled = false

        viewPagerListener()
    }

    private fun viewPagerListener() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.navBottomView.menu.findItem(R.id.home).isChecked = true
                    }
                    1 -> {
                        binding.navBottomView.menu.findItem(R.id.chats).isChecked = true
                    }
                    else -> {
                        binding.navBottomView.menu.findItem(R.id.profile).isChecked = true
                    }
                }
            }
        })

    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser!!
        val map = mutableMapOf<String, String>()
        map["status"] = "online"
        FirebaseFirestore.getInstance().collection("users").document(user.uid)
            .set(map, SetOptions.merge())
    }

    override fun onPause() {
        super.onPause()
        val user = FirebaseAuth.getInstance().currentUser!!
        val map = mutableMapOf<String, String>()
        map["status"] = "offline"
        FirebaseFirestore.getInstance().collection("users").document(user.uid)
            .set(map, SetOptions.merge())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.viewPager.currentItem = when (item.itemId) {
            R.id.home -> {
                0
            }
            R.id.chats -> {
                1
            }
            else -> {
                2
            }
        }
        return true
    }
}