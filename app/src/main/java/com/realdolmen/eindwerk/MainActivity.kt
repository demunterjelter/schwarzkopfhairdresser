package com.realdolmen.eindwerk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var userName: String? = null
    private var password: String? = null
    private var authentication: FirebaseAuth? = null

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 7


    //google login -----------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialise()

        FirebaseApp.initializeApp(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        var signIn: SignInButton = findViewById(R.id.sign_in_button)
        //var signOut: Button = findViewById(R.id.signout)
        signIn.setOnClickListener(this)
        //signOut.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.sign_in_button -> signIn()
            //R.id.signout -> signOut()
            else -> println("Number too high")
        }
    }


    private fun signIn() {

        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            println("ge geraakt er")
            val i = Intent(this, HomePageActivity::class.java)

            startActivity(i)
        } catch (e: ApiException) {
            Log.w("test", "signInResult:failed code=" + e.statusCode)
        }

    }


    fun goToRegisterActivity(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        intent.action = Intent.ACTION_SEND
        intent.type = "text/plain";
        intent.putExtra(Intent.EXTRA_TEXT, "Welcome to the register activity");
        startActivity(intent)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }*/


    //gwn email login ---------------------------------------------
    private fun initialise() {
        authentication = FirebaseAuth.getInstance()
        loginBtn!!.setOnClickListener {

            userName = et_usernameLogin.text.toString()
            password = et_passwordLogin.text.toString()

            loginUser()
        }
    }

    private fun loginUser() {

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            authentication!!.signInWithEmailAndPassword(userName!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@MainActivity, HomePageActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@MainActivity, "Fout bij e-mail of password.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }


}
