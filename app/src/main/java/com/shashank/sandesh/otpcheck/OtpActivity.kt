package com.shashank.sandesh.otpcheck

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.shashank.sandesh.R
import com.shashank.sandesh.databinding.ActivityOtpBinding
import com.shashank.sandesh.profile.profileActivity
import kotlinx.android.synthetic.main.activity_otp.*
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtpBinding

    //if  code send failed
    private lateinit var resendingToken :PhoneAuthProvider.ForceResendingToken


    private lateinit var mCallback:PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var mVerificationId:String

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progress :ProgressDialog




    lateinit var mobileNumber:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        supportActionBar?.hide()
        binding= ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mobileNumber= intent.getStringExtra("mobile").toString()
        binding.verifyTv.text=  "Verify $mobileNumber"
        progress = ProgressDialog(this)
        progress.setMessage("Loading...")
        progress.setCanceledOnTouchOutside(false)

      progress.show()


        init()


    }



    private fun init() {


        binding.sendNBtn.setOnClickListener {
            progress.show()

            val credential = PhoneAuthProvider.getCredential(mVerificationId!!,binding.firstPinView.text.toString())
            signInWithPhoneAuthCredential(credential)
        }


        firebaseAuth= FirebaseAuth.getInstance()


        mCallback =object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
              ///Automatic Code Complete
                progress.dismiss()
                 val smsCode = credential.smsCode
                binding.firstPinView.setText(smsCode)
                signInWithPhoneAuthCredential(credential)


            }

            override fun onVerificationFailed(p0: FirebaseException) {

               Snackbar.make(findViewById(android.R.id.content),"${p0.localizedMessage}",Snackbar.LENGTH_SHORT).show()

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                progress.dismiss()
                mVerificationId = p0
            }


        }

        startPhoneNumberVerfication(mobileNumber)
    }

    private fun startPhoneNumberVerfication(mob: String?) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(mob.toString())       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(mCallback)          // OnVerificationStateChangedCallbacks
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }



    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                            if(task.result?.additionalUserInfo?.isNewUser == true){


                            }else{
                                Toast.makeText(this,"Old User",Toast.LENGTH_SHORT).show()
                                //startActivity(Intent(this,OldProfileActivity::class.java))
                                //finish()
                            }

                       Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()



                        startActivity(Intent(this,profileActivity::class.java))
                        finish()
                    } else {
                        // Sign in failed, display a message and update the UI
                        Toast.makeText(this,"${task.exception?.localizedMessage}",Toast.LENGTH_SHORT).show()
                            progress.dismiss()

                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                        }
                        // Update UI
                    }
                }
    }
}