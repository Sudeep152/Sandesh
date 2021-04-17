package com.shashank.sandesh.chatscreen

import UIutils
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sandesh.R
import com.shashank.sandesh.adapters.ChatAdapter
import com.shashank.sandesh.messageDao.messageDao
import com.shashank.sandesh.model.User
import com.shashank.sandesh.model.messageModel
import kotlinx.android.synthetic.main.activity_chat_roomctivity.*


class ChatRoomctivity : AppCompatActivity() {

    lateinit var adapter:ChatAdapter



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_roomctivity)
        supportActionBar!!.hide()

        settingChat()
        showImg()

        setupRV()
        

    }


    private fun setupRV() {

        var messageDao= messageDao()


        val query = messageDao.msgCollection
        val recyclerOptions = FirestoreRecyclerOptions.Builder<messageModel>().setQuery(query, messageModel::class.java).build()

   adapter= ChatAdapter(recyclerOptions)
        listchatRv.layoutManager= LinearLayoutManager(applicationContext)

        listchatRv.adapter= adapter


    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showImg() {
        val  img :String= intent.getStringExtra("profileImg").toString()

        val preferences = getSharedPreferences("AUTHENTICATION_FILE_NAME", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("url", img);
        editor.apply();

        val prfs = getSharedPreferences("AUTHENTICATION_FILE_NAME", MODE_PRIVATE)
        val saveImg = prfs.getString("url", "")

        userImg.setOnClickListener { v1 -> UIutils(this).showPhoto(Uri.parse(saveImg)) }

        sendBtn.setOnClickListener {
            messageSetUp()
            Toast.makeText(this, "click", Toast.LENGTH_SHORT).show()

        }


    }

    private fun settingChat() {
        val name:String=intent.getStringExtra("name").toString()
        val  img :String= intent.getStringExtra("profileImg").toString()
        username.text=name
        Glide.with(this)
            .load(img)
            .circleCrop()
            .into(userImg)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun messageSetUp(){

        val recUid:String = intent.getStringExtra("recUid").toString()
        //Toast.makeText(this, "$recUid", Toast.LENGTH_SHORT).show()

        var senderUser = FirebaseAuth.getInstance()
        val msg = messageDao()
        val msgModel = messageModel(recUid,senderUser.uid.toString(),msgEdt.text.toString())

        msg.chatStart(msgModel)




    }

}