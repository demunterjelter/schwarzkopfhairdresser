package com.realdolmen.eindwerk

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var authentication: FirebaseAuth? = null
    private var userName: String? = null
    private var password: String? = null

    private fun initialise() {

        authentication = FirebaseAuth.getInstance()
        btn_register!!.setOnClickListener {
            userName = et_user_name.text.toString()
            password = et_password.text.toString()
            createNewAccount()
        }
    }

    fun createNewAccount() {
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            authentication!!.createUserWithEmailAndPassword(userName!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this@RegisterActivity, "Account succesvol aangemaakt.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@RegisterActivity, "fout bij het registreren(moet een email zijn).",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initialise()
    }
}
