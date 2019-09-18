package com.anything.koinfirebase.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.anything.koinfirebase.R
import com.anything.koinfirebase.model.Status
import com.anything.koinfirebase.model.User
import com.anything.koinfirebase.util.JsonPrinter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val _viewModel: MainActivityViewModel by viewModel()
    private val printer = JsonPrinter(MainActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_register.setOnClickListener {
            register()
        }

        _viewModel.subscribeProgressBarStatus()
            .observe(this, Observer {
                if (it) {
                    progress_bar.visibility = View.VISIBLE
                } else{
                    progress_bar.visibility = View.GONE
                }
            })
        _viewModel.subscribeUsersChange()
            .observe(this, Observer {
                tv_json_users.text = printer.info(it)
            })

    }

    private fun register() {
        val user = User()
        val email = et_email.text.toString()
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        if (email == "") {
            et_email.error = "Email cannot be null"
        } else if (username == "") {
            et_username.error = "Username cannot be null"
        } else if (password == "") {
            et_password.error = "Password cannot be null"
        } else {
            user.email = email
            user.username = username
            _viewModel.createUserWithEmailAndPasword(user, password)
            _viewModel.subscribeRegistrationStatus()
                .observe(this, Observer {
                    when {
                        it.status == Status.SUCCESS -> {
                            Toast.makeText(this, it.detailMessage, Toast.LENGTH_LONG).show()
                            tv_current_user.text = _viewModel.getCurrentUser()?.email
                        }
                        it.status == Status.FAILED -> {
                            Toast.makeText(this, it.detailMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                })
        }

    }


    override fun onStart() {
        super.onStart()
        val currentUser = _viewModel.getCurrentUser()
        if (currentUser == null) {
            printer.warn("No user logon!!")
        } else {
            printer.info(currentUser)
        }
    }
}
