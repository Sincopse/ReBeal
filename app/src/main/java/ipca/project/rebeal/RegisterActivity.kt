package ipca.project.rebeal

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import ipca.project.rebeal.databinding.RegisterProfileBinding
import ipca.project.rebeal.ui.isPasswordValid
import ipca.project.rebeal.ui.isValidEmail


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding: RegisterProfileBinding
    var firestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        binding = RegisterProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.RegistarButton.setOnClickListener {

            val email = binding.editTextEmailAddress.text.toString()
            val password = binding.editTextPasswordRegisto.text.toString()
            val password2 = binding.editTextPasswordRegistoConfirm.text.toString()

            if (password != password2){
                Toast.makeText(
                    baseContext,
                    "Passwords do not match.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            if (!password.isPasswordValid()){
                Toast.makeText(
                    baseContext,
                    "Password must have at least 6 chars.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            if (!email.isValidEmail()){
                Toast.makeText(
                    baseContext,
                    "Email is not valid.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)

                        saveUserToFirestore()
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.${task.exception}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }

    private fun saveUserToFirestore() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val username = binding.editTextUserName.text.toString()

        val user = hashMapOf(
            "uid" to uid,
            "username" to username,
        )

        firestore.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "User saved to Firestore")
            }
            .addOnFailureListener {
                Log.e("RegisterActivity", "Failed to save user to Firestore: ${it.message}")
            }

    }

}