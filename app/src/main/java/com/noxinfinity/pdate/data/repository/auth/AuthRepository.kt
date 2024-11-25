package com.noxinfinity.pdate.data.repository.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    fun signInUser(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("AuthRepository",user.toString());
                } else {
                    Log.d("AuthRepository", "Sign-in failed: ${task.exception?.message}");
                }
            }
    }

    fun signUpUser(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
    }
}