package com.shashank.sandesh.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.shashank.sandesh.R
import com.shashank.sandesh.databinding.ActivityMobileBinding
import com.shashank.sandesh.otpcheck.OtpActivity
import kotlinx.android.synthetic.main.activity_mobile.*

class MobileActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityMobileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mobile)

        binding = ActivityMobileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide();


        mobileET.addTextChangedListener {
            nextBtn.isEnabled = !(it.isNullOrEmpty() || (it.length <10))
        }

        binding.nextBtn.setOnClickListener {

            if(mobileET.text.length >10){
                Toast.makeText(this,"Please enter valid number",Toast.LENGTH_SHORT).show()
            }else{

                val country = cpp.selectedCountryCodeWithPlus.toString()
                val mobileNumber = country+ mobileET.text.toString()
                Toast.makeText(this,"Mobile number is $mobileNumber",Toast.LENGTH_SHORT).show()

                val intent =Intent(this,OtpActivity::class.java)
                intent.putExtra("mobile",mobileNumber)
                startActivity(intent)
                finish()
            }


        }


    }
}