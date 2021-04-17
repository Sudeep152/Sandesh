package com.shashank.sandesh.userDao

import android.provider.Settings
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.shashank.sandesh.model.userModel
import com.shashank.sandesh.profile.profileActivity
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class userDao {

    val db = FirebaseFirestore.getInstance()
    val collectionDb =db.collection("users")

    fun addUserInfo(user:userModel){

        user.let {
            GlobalScope.launch(Dispatchers.IO) {
                collectionDb.document(user.uid).set(it)
            }



        }



    }
}