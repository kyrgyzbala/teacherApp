package com.english.teacherapp.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.english.teacherapp.ui.chat.ChatFragment
import com.english.teacherapp.ui.home.HomeFragment
import com.english.teacherapp.ui.profile.ProfileFragment

class MainPagerAdapter (fm: FragmentActivity) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> HomeFragment()
            1 -> ChatFragment()
            else -> ProfileFragment()
        }
    }
}