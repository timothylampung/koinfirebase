package com.anything.koinfirebase.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anything.koinfirebase.model.RegistrationStatus
import com.anything.koinfirebase.model.Status
import com.anything.koinfirebase.model.User
import com.anything.koinfirebase.util.JsonPrinter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class MainActivityViewModel(
    private val fbd: FirebaseDatabase,
    private val fba: FirebaseAuth,
    private val gson: Gson
) : ViewModel() {

    private val ref = this.fbd.getReference("myapplicationref")
    private val printer = JsonPrinter(MainActivityViewModel::class.java)

    /*Live Data*/
    private val _users = MutableLiveData<List<User>>()
    private val _registrationStatus = MutableLiveData<RegistrationStatus>()
    private val _progressBarStatus = MutableLiveData<Boolean>().apply { value = false }

    fun logOut() {
        fba.signOut()
    }

    fun getCurrentUser(): FirebaseUser? = fba.currentUser

    fun createUserWithEmailAndPasword(user: User, password: String) {
        _progressBarStatus.postValue(true)
        fba.createUserWithEmailAndPassword(user.email!!, password)
            .addOnCompleteListener { task ->
                _progressBarStatus.postValue(false)
                var o = RegistrationStatus()
                if (task.isSuccessful) {
                    o.detailMessage = "User Registration Success!"
                    o.status = Status.SUCCESS
                    writeUser(user)
                } else {
                    o = gson.fromJson(
                        gson.toJson(task.exception!!),
                        RegistrationStatus::class.java
                    )
                    o.status = Status.FAILED
                }
                _registrationStatus.postValue(o)
            }
    }

    fun subscribeRegistrationStatus(): LiveData<RegistrationStatus> = _registrationStatus


    private fun writeUser(user: User) {
        _progressBarStatus.postValue(true)
        ref.child("users")
            .push()
            .setValue(user)

        ref.child("users")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    printer.warn(p0)
                    _progressBarStatus.postValue(false)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    val users = ArrayList<User>()
                    p0.children.forEach {
                        val u: User? = it!!.getValue(User::class.java)
                        u!!.key = it.key
                        users.add(u)
                    }
                    _progressBarStatus.postValue(false)
                    _users.postValue(users)
                }
            })
    }

    fun subscribeUsersChange(): LiveData<List<User>> = _users

    fun subscribeProgressBarStatus(): LiveData<Boolean> = _progressBarStatus
}