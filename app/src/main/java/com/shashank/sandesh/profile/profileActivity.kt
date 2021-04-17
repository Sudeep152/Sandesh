package com.shashank.sandesh.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.shashank.sandesh.R
import com.shashank.sandesh.databinding.ActivityProfileBinding
import com.shashank.sandesh.home.HomeActivity
import com.shashank.sandesh.model.userModel
import com.shashank.sandesh.userDao.userDao
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File

class profileActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth



    lateinit var binding: ActivityProfileBinding
    private var imgaeUri: Uri? =null

    private lateinit var strorageRef:FirebaseStorage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        firebaseAuth= FirebaseAuth.getInstance()
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        strorageRef = FirebaseStorage.getInstance()

        binding.addImg.setOnClickListener {

            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }


        binding.oldNBtn.setOnClickListener {
           if(binding.nameET.text.isEmpty() || binding.bioET.text.isEmpty()){
               Snackbar.make(findViewById(android.R.id.content),"Please fill all data",Snackbar.LENGTH_SHORT).show()
           }else{
               allDataUpload()
               startActivity(Intent(this, HomeActivity::class.java))
               Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()
               finish()
           }

        }


      //  id.text = firebaseAuth.currentUser.phoneNumber.toString()


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data

            Glide
                .with(this)
                .load(fileUri)
                .centerCrop()
                .placeholder(R.drawable.person)
                .into(binding.pfImage);
//            profileImage.setImageURI(fileUri)


            imgaeUri=fileUri
            //You can get File object from intent
            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath:String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun allDataUpload() {

        var download ="NoImage"


        if(imgaeUri !=null){

            var storageForImage= strorageRef.reference.child("ProfileImages/${FirebaseAuth.getInstance().currentUser?.uid.toString()}")

            val uploadTask =storageForImage.putFile(imgaeUri!!)
            val urlTask= uploadTask?.continueWithTask{ task->
                if (!task.isSuccessful){
                    Toast.makeText(this,"UploadTask",Toast.LENGTH_LONG).show()
                }
                storageForImage.downloadUrl
            }.addOnCompleteListener { task->
                if(task.isSuccessful){
                    download=task.result.toString()

//                    val userDataClass=CreateUser(name,currentUserId,bio,download)
//                    firebaseDB.collection("Users").document(FirebaseAuth.getInstance().currentUser?.uid.toString()).set(userDataClass)
//                        .addOnCompleteListener { task->
//                            if(task.isSuccessful){
//                                Toast.makeText(this,"Successfully",Toast.LENGTH_SHORT).show()
//                            }else{
//                                Toast.makeText(this,"sometiong"+task.exception.toString(),Toast.LENGTH_SHORT).show()
//                            }
//                        }


                    val user = userModel(
                        firebaseAuth.currentUser!!.uid,binding.nameET.text.toString(),
                        firebaseAuth.currentUser!!.phoneNumber.toString()
                        ,binding.bioET.text.toString() ,download)

                    val userDao=userDao()

                    userDao.addUserInfo(user)



                }

            }

        }




        val user = userModel(
            firebaseAuth.currentUser!!.uid,binding.nameET.text.toString(),
            firebaseAuth.currentUser!!.phoneNumber.toString()
            ,binding.bioET.text.toString() )

        val userDao=userDao()
        userDao.addUserInfo(user)
    }

}