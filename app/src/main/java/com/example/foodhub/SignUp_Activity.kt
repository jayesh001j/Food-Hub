package com.example.foodhub

import AuthHelper.Companion.authHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodhub.databinding.ActivitySignUpBinding

class SignUp_Activity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.signInId.setOnClickListener{
            finish()
        }

        binding.btnRegister.setOnClickListener{

            val email = binding.emailId.text.toString()
            val password = binding.passwordId.text.toString()
            val cpassword = binding.confirmPasswordId.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty()) {
                if(password != cpassword) {
                    Toast.makeText(this, "password and conform password not match", Toast.LENGTH_SHORT).show()
                } else {
                    authHelper.signUp(email, password)
                    finish()
                }
            }

            else {
                Toast.makeText(this, "Required email and password", Toast.LENGTH_SHORT).show()
            }

        }


    }
}