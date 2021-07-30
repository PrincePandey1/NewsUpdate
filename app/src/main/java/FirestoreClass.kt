import android.app.Activity
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.example.android.newsupdate.activity.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import model.User

class FirestoreClass: BaseActivity() {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun  registerUser(activity: SignUp, userInfo: User){
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo , SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                    e->
                Log.e(activity.javaClass.simpleName,"Something went Wrong")
            }
    }

    fun getCurrentUserId(): String {
        //  return FirebaseAuth.getInstance().currentUser!!.uid
        // In Alternative we check whether the Current User is empty or not
        val currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserID = " "
        if(currentUser != null){
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun loadUserData(activity: Activity){

        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->
                val loggedInUser = document.toObject(User::class.java)

                when(activity){
                    is IntroActivity ->{
                        activity.signInSuccess(loggedInUser)
                    }


                }

            }.addOnFailureListener {
                    e->
                when(activity){
                    is IntroActivity ->{
                        activity.hideProgressDialog()
                    }


                }

                Log.e("SignInSuccess","Something went Wrong")
            }
    }

}