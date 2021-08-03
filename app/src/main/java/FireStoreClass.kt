import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.android.newsupdate.activity.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import model.User

class FireStoreClass: BaseActivity() {

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
                         is MainActivity ->{
                             if (loggedInUser != null) {
                                 activity.updateNavigationUserDetails(loggedInUser)
                             }
                         }
                         is MyProfileActivity ->{
                             if (loggedInUser != null) {
                                 activity.setUserDataInUI(loggedInUser)
                             }
                         }
                     }

                 }.addOnFailureListener {
                     e->

                     when(activity){
                         is IntroActivity ->{
                             activity.hideProgressDialog()
                         }
                         is MyProfileActivity ->{
                             activity.hideProgressDialog()
                         }
                     }
                     Log.e("SignInUser" , "Error writing document" , e)
                 }
     }
    fun updateUserProfileData(activity: MyProfileActivity,userHashMap:  HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS)
                .document(getCurrentUserId())
                .update(userHashMap)
                .addOnSuccessListener {
                    Log.e(activity.javaClass.simpleName,"Profile Data updated")
                    Toast.makeText(activity,"Profile updated successfully",Toast.LENGTH_LONG).show()
                    activity.profileUpdateSuccess()
                }.addOnFailureListener {
                    e->
                    activity.hideProgressDialog()
                    Log.e(activity.javaClass.simpleName,"Error while creating a board.")
                    Toast.makeText(activity,"Something went wrong!",Toast.LENGTH_LONG).show()
                }


                }
    }

