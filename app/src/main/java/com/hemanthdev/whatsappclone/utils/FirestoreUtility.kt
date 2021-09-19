package com.hemanthdev.whatsappclone.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.hemanthdev.whatsappclone.data.model.User
import javax.inject.Inject
import javax.inject.Singleton

const val users = "users"
const val status = "status"
const val chats = "chats"
const val messages = "messages"

class FirestoreUtility @Inject constructor() {

    private val db by lazy { Firebase.firestore }
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val currentUserId: String
        get() = firebaseAuth.currentUser?.uid ?: ""


    /**
     * Check for user details
     */
    fun checksForUserDetails(callbacks: FirestoreCallbacks) {
        if (firebaseAuth.currentUser != null) {
            val userId = currentUserId
            db.collection(users)
                .document(userId)
                .get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        val userDetails = it.toObject<User>()
                        userDetails?.let {
                            callbacks.userDetails(user = userDetails)
                        } ?: kotlin.run {
                            callbacks.isFalse()
                        }
                    } else {
                        callbacks.isFalse()
                    }

                }.addOnFailureListener {
                    callbacks.onError(it.localizedMessage ?: "")
                }
        }
    }

    fun setUserDetails(data: User, callbacks: FirestoreCallbacks) {
        db.collection(users)
            .document(currentUserId)
            .set(data, SetOptions.merge()).addOnSuccessListener {
                callbacks.isTrue()
            }.addOnFailureListener {
                callbacks.onError(it.localizedMessage ?: "")
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun userDetailsListener(callbacks: FirestoreCallbacks) {
        db.collection(users).document(currentUserId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    callbacks.onError(e.localizedMessage ?: "")
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val data = snapshot.toObject<User>()
                    if (data != null) {
                        callbacks.userDetails(user = data)
                    } else {
                        callbacks.onError("")
                    }
                } else {
                    callbacks.onError("")
                }
            }
    }

    fun allUsers(getCurrentAlso: Boolean = false, callbacks: FirestoreCallbacks) {
        db.collection(users)
            .whereNotEqualTo(
                DocumentField.userId, if (getCurrentAlso) "" else currentUserId
            )
            .addSnapshotListener { value, e ->
                if (e != null) {
                    callbacks.onError(e.localizedMessage ?: "")
                    return@addSnapshotListener
                }

                val users = ArrayList<User>()
                if (value != null) {
                    for (doc in value) {
                        users.add(doc.toObject())
                    }

                    users.sortBy { it.name }

                    callbacks.userList(users = users)
                } else {
                    callbacks.onError("")
                }
            }
    }

}