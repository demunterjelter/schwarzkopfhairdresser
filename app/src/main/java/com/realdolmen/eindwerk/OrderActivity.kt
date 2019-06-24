package com.realdolmen.eindwerk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_page.nav_view
import kotlinx.android.synthetic.main.activity_order.*


class OrderActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    private var authentication: FirebaseAuth? = null


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                var authentication: FirebaseAuth? = FirebaseAuth.getInstance()
                if (authentication!!.currentUser != null) {

                    authentication.signOut()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return true
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    return true
                }

            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)


        var button = findViewById<Button>(R.id.btn_submitToOrderListActivity)
        button.setOnClickListener {
            orderAndGoToOrderList()
        }

        nav_view.selectedItemId = R.id.navigation_order
        nav_view.setOnNavigationItemReselectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home ->
                    startActivity(Intent(this, HomePageActivity::class.java))
            }
            when (item.itemId) {
                R.id.navigation_products ->
                    startActivity(Intent(this, ProductActivity::class.java))
            }
            when (item.itemId) {
                R.id.navigation_order ->
                    startActivity(Intent(this, OrderActivity::class.java))
            }
            when (item.itemId) {
                R.id.navigation_orderlist ->
                    startActivity(Intent(this, OrderListActivity::class.java))
            }
            when (item.itemId) {
                R.id.navigation_appointment ->
                    startActivity(Intent(this, AppointmentActivity::class.java))
            }
            //true
        }


    }

    fun orderAndGoToOrderList() {

        val order = HashMap<String, Any>()
        //order["id"] = "1"
        order["email"] = tv_emailEdit.text.toString()
        order["firstname"] = tv_firstnameEdit.text.toString()
        order["lastname"] = tv_lastnameEdit.text.toString()
        order["instantBlushAmount"] = tv_InstantBlushBedrag.text.toString()
        order["blushWash"] = tv_BlushWashBedrag.text.toString()
        order["keratinRestoreMask"] = tv_KeratinRestoreMaskBedrag.text.toString()
        order["keratinRestoreShampoo"] = tv_KeratinRestoreShampooBedrag.text.toString()
        order["threedMenHairShampoo"] = tv_ThreedHairShampooBedrag.text.toString()
        order["threedMenMoldingWax"] = tv_ThreedMoldingWaxBedrag.text.toString()
        order["threedMenStrongHoldGel"] = tv_ThreedGelBedrag.text.toString()
        order["treatment"] = tv_TreatmentBedrag.text.toString()
        order["vibrantRedMicellarShampoo"] = tv_VibrantMicellarShampooBedrag.text.toString()
        order["xxlRichMicellarShampoo"] = tv_XxlMicellarShampooBedrag.text.toString()
        order["repairRescueMicellarShampoo"] = tv_RepairRescueShampooBedrag.text.toString()
        order["repairRescueTreatment"] = tv_repairRescueTreatmentBedrag.text.toString()
        order["repairRescueXxlSealedEnds"] = tv_RepairRescuexxlBedrag.text.toString()
        order["moistureKickMicellarCleansingConditioner"] = tv_MoistureKickCleansingBedrag.text.toString()
        order["moistureKickMicellarShampoo"] = tv_MoistureKickShampooBedrag.text.toString()
        order["moistureKickTreatment"] = tv_MoistureKickTreatmentBedrag.text.toString()

        authentication = FirebaseAuth.getInstance()
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        var userThatIsLoggedIn: String? = null

        if (authentication!!.currentUser != null) {
            if (authentication!!.currentUser!!.email!!.contains("hotmail.com", ignoreCase = true)) {

                println("nu komt de gwn email")
                //println(authentication!!.currentUser!!.email)
                println("in de eerste if")
                userThatIsLoggedIn = authentication!!.currentUser!!.email


            }
        } else {

            // println(authentication!!.currentUser!!.email)
            println("in de else")
            userThatIsLoggedIn = acct!!.email

        }


        if (tv_emailEdit.text.toString() == userThatIsLoggedIn) {
            val intent = Intent(this, OrderListActivity::class.java)
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            //intent.putExtra(Intent.EXTRA_TEXT, "Welcome to the second activity" )
            startActivity(intent)

            db.collection("users")
                .add(order)
                .addOnSuccessListener { documentReference ->
                    println("succes")
                    //documentReference.id
                }
                .addOnFailureListener { e ->
                    println("failure")
                }

        } else {
            Toast.makeText(this, "moet zelfde email zijn dan login", Toast.LENGTH_LONG).show()
        }


    }
}
