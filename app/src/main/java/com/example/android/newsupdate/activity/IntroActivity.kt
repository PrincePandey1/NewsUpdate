package com.example.android.newsupdate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.android.newsupdate.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_intro.*
import model.User

class IntroActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        auth = FirebaseAuth.getInstance()

        Intro_signUp_text.setOnClickListener {
            startActivity(Intent(this,SignUp::class.java))
        }

        btn_sign_in_intro.setOnClickListener {
              signInUser()
        }
    }


    private fun signInUser(){
        val email: String = et_email_signIn.text.toString().trim { it <= ' ' }
        val password: String = et_password_signIn.text.toString().trim { it <= ' ' }

        if(validateForm(email, password)){
            showProgressDialog("Please wait..")

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){  task ->
                    if(task.isSuccessful){
                        hideProgressDialog()

                        var user = auth.currentUser

                        startActivity(Intent(this,MainActivity::class.java))
                    }else{
                        Toast.makeText(this,"check your internet connection",Toast.LENGTH_SHORT).show()
                    }

                }

                }
        }


    private fun validateForm( email: String, password:String): Boolean {

        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Please enter a email")
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar("Please enter a Password")
                false
            }
            else -> {
                return true
            }
        }
    }
    fun signInSuccess(loggedInUser: User?) {
        hideProgressDialog()
        startActivity(Intent(this, MainActivity::class.java))
        finish()

    }


}