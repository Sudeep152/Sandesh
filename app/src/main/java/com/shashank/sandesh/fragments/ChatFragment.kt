package com.shashank.sandesh.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.shashank.sandesh.R
import com.shashank.sandesh.databinding.FragmentChatBinding
import kotlinx.android.synthetic.main.fragment_chat.*

@Suppress("UNREACHABLE_CODE")
class ChatFragment : Fragment() {

    private  var _binding:FragmentChatBinding ?=null
    private val binding =_binding
    var db = FirebaseFirestore.getInstance()
    var firebaseAuth= FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val docRef= db.collection("users").document(firebaseAuth.currentUser!!.uid)

        val source = Source.CACHE

// Get the document, forcing the SDK to use the offline cache
        docRef.get(source).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Document found in the offline cache
                val document = task.result
                //binding.id.text= document!!.data.toString()


               // binding!!.textView6.text = document!!.data.toString()
                textView6.text=document!!.data.toString()


            }
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)

        _binding = FragmentChatBinding.inflate(layoutInflater,container,false)

        return binding!!.root




    }


}