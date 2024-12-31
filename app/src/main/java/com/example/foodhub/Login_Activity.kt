package com.example.foodhub

import AuthHelper.Companion.authHelper
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodhub.databinding.ActivityLoginBinding
import com.example.my_app.helper.FireStoreHelper.Companion.fireStoreHelper
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Login_Activity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Google Sign In

        val googleSignOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id)).requestEmail().build()

        googleClient = GoogleSignIn.getClient(this, googleSignOption)

        val registerGoogle =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                try {
                    val googleId = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                    val credential = GoogleAuthProvider.getCredential(googleId.result.idToken, null)

                    authHelper.auth.signInWithCredential(credential).addOnSuccessListener {
                        Log.e("RUN", "onCreate: Run is running....", )
                        val email = authHelper.auth.currentUser!!.email;
                        val name = authHelper.auth.currentUser!!.displayName;

                        if (email != null && name!= null) {
                            fireStoreHelper.addUserData(email,name)
                        }
                        val intent = Intent(this, Home_Activity::class.java)
                        startActivity(intent)
                        finish()
                    }
                        .addOnFailureListener {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }

                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }

        binding.signUpId.setOnClickListener {
            val intent = Intent(this, SignUp_Activity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {

            val email = binding.emailId.text.toString()
            val password = binding.passwordId.text.toString()


            GlobalScope.launch {
                val msg = authHelper.signIn(email, password)

                Log.e("MSG", "onCreate: $msg")

                if (msg == "Success") {
                    fireStoreHelper.addUserData(email, password)

                    val intent = Intent(this@Login_Activity, Home_Activity::class.java)
                    startActivity(intent)
                    finish()




                } else if (msg == "The supplied auth credential is incorrect, malformed or has expired.") {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            this@Login_Activity,
                            "password is incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else if (msg == "The email address is badly formatted.") {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            this@Login_Activity,
                            "Email is wrong",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Handler(Looper.getMainLooper()).post {
                        Toast.makeText(
                            this@Login_Activity,
                            "your email and password is wrong.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.googleBtn.setOnClickListener {
            val intent = googleClient.signInIntent
            registerGoogle.launch(intent)


        }

    }


}