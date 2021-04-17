package com.shashank.sandesh.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shashank.sandesh.fragments.ChatFragment
import com.shashank.sandesh.fragments.UserFragment

class viewPagerAdapter(fragmentManager: FragmentManager,lifecycle:Lifecycle):FragmentStateAdapter(fragmentManager,lifecycle){
    override fun getItemCount(): Int {
      return 2;
    }

    override fun createFragment(position: Int): Fragment {

     return   when(position){
            0->{
                ChatFragment()
            }
            1->{
               UserFragment()
            }
            else->{
                Fragment()
            }
        }
    }

}