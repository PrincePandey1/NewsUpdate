package com.example.android.newsupdate.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.android.newsupdate.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_intro.*
import model.User

class IntroActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private companion object{
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

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

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString((R.string.default_web_client_id)))
            .requestEmail()  //we only need email from google account
            .build()

        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions)



       // checkUser()

        //click btn to create google SignIn
        btn_google_signIn.setOnClickListener {
            Log.d(TAG,"onCreate: begin Google Sign")
            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent , RC_SIGN_IN)
        }
    }//onCreate

   /* private fun checkUser() {
        //check if User is logged in or not
        val firebaseUser = auth.currentUser
        if(firebaseUser != null){
            //User is already loggedIn
                startActivity(Intent(this@IntroActivity,MainActivity::class.java))
            finish()
        }
    }*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            Log.d(TAG,"onActivityResult: Google SignIn Intent Result" )
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{

                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)

            }
            catch (e: Exception){
                //failed google SignIn
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?) {
        Log.d(TAG,"FirebaseAuth with googleAccount")

        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener(this) { authResult ->

                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithCredential:success")
                val user = auth.currentUser

                //get User info
                val uid = user!!.uid
                val email = user!!.email

                Log.d(TAG,"firebaseAuthWithGoogleAccount: Email: $email")
                Log.d(TAG,"firebaseAuthWithGoogleAccount: UId: $uid")

                //check if user is new or existing
                if(authResult.additionalUserInfo!!.isNewUser){
                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Account Created.....\n$email")
                    Toast.makeText(this@IntroActivity , "Account created...\n$email",Toast.LENGTH_SHORT).show()

                }else{
                    Log.d(TAG,"firebaseAuthWithGoogleAccount: Existing user.....\n$email")
                    Toast.makeText(this@IntroActivity , "LoggedIn...\n$email",Toast.LENGTH_SHORT).show()
                }
                //start  activity
                startActivity(Intent(this@IntroActivity,MainActivity::class.java))
                finish()
            }.addOnFailureListener {it->
                Log.d(TAG,"firebaseAuthWithGoogleAccount: Loggin Failed due to ${it.message}")
                Toast.makeText(this@IntroActivity , "LoggedIn failed...\n${it.message}",Toast.LENGTH_SHORT).show()
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