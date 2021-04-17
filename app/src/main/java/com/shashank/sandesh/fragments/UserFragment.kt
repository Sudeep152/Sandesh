package com.shashank.sandesh.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.shashank.sandesh.R
import com.shashank.sandesh.adapters.firebaseAdapter
import com.shashank.sandesh.model.User
import com.shashank.sandesh.model.userModel
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    private lateinit var adapter: firebaseAdapter
    var db = FirebaseFirestore.getInstance()
    var firebaseAuth = FirebaseAuth.getInstance()

    val collectionReference :CollectionReference=db.collection("users")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

    }

    private fun setupRV() {

     //   val docRef= db.collection("users")
        val uid:String = FirebaseAuth.getInstance().currentUser!!.uid;
        val query:Query = collectionReference.whereNotEqualTo("uid", uid);
        val recyclerOptions =FirestoreRecyclerOptions.Builder<User>().setQuery(query,User::class.java).build()




        adapter= firebaseAdapter(recyclerOptions)
        rv.layoutManager=LinearLayoutManager(context)

        rv.adapter=adapter

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)



    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}