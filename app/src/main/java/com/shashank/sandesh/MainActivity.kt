package com.shashank.sandesh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.shashank.sandesh.databinding.ActivityMainBinding
import com.shashank.sandesh.home.HomeActivity
import com.shashank.sandesh.mobile.MobileActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()


    }

    override fun onStart() {
        super.onStart()
        val currentUser= firebaseAuth.currentUser
        updateUI(currentUser)

    }

    private fun updateUI(firebaseUser: FirebaseUser?) {

        if(firebaseUser !=null){

            startActivity(Intent(this, HomeActivity::class.java))
            finish()

        }else{
            binding.nextBtn.setOnClickListener {
                startActivity(Intent(this,MobileActivity::class.java))

            }

        }
    }
}