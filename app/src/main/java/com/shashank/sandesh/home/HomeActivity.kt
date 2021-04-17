package com.shashank.sandesh.home

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.ktx.Firebase
import com.shashank.sandesh.MainActivity
import com.shashank.sandesh.R
import com.shashank.sandesh.adapters.viewPagerAdapter
import com.shashank.sandesh.databinding.ActivityHomeBinding
import com.shashank.sandesh.showprofile.ShowProfileActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    var db =FirebaseFirestore.getInstance()
    var firebaseAuth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val docRef= db.collection("users").document(firebaseAuth.currentUser!!.uid)

        val source = Source.CACHE



        binding.appBar
        val adapter =viewPagerAdapter(supportFragmentManager,lifecycle)


        binding.viewpage.adapter= adapter
        TabLayoutMediator(binding.tablayout,binding.viewpage){
            tab,postion->
            when(postion){
                0->{
                    tab.text="Chat"
                }
                1->{
                    tab.text="User"
                }


            }
        }.attach()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater:MenuInflater = menuInflater
        menuInflater.inflate(R.menu.menu_home,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        when(item.itemId){
            R.id.profile -> profile()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun profile() {
       startActivity(Intent(this,ShowProfileActivity::class.java))

    }
}