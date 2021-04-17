package com.shashank.sandesh.showprofile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.shashank.sandesh.R
import com.shashank.sandesh.databinding.ActivityHomeBinding
import com.shashank.sandesh.databinding.ActivityShowProfileBinding
import com.shashank.sandesh.profile.profileActivity
import kotlinx.android.synthetic.main.activity_show_profile.*

class ShowProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowProfileBinding


    var db = FirebaseFirestore.getInstance()
    var firebaseAuth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_profile)

        binding = ActivityShowProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()


        fetchData()


        binding.oldEdit.setOnClickListener {

            startActivity(Intent(this,profileActivity::class.java))

        }



    }

    private fun fetchData() {
        val docRef= db.collection("users").document(firebaseAuth.currentUser!!.uid)

        val source = Source.CACHE

// Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                //binding.id.text= document!!.data.toString()

                binding.oldpfName.text = document!!.getString("name")
                binding.oldpfBio.text =document.getString("bio")

                Glide
                    .with(this)
                    .load(document.getString("profile"))
                    .fitCenter()
                    .placeholder(R.drawable.person)
                    .into(binding.pfImage);



            } else {
                Toast.makeText(this, "Cached get failed: ", Toast.LENGTH_SHORT).show()
            }
        }



    }
}