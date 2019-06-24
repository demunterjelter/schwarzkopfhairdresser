package com.realdolmen.eindwerk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.realdolmen.eindwerk.adapter.OrderListAdapter
import com.realdolmen.eindwerk.data.Order
import kotlinx.android.synthetic.main.activity_home_page.nav_view
import kotlinx.android.synthetic.main.activity_order_list.*


class OrderListActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    val allOrders = arrayListOf<Order>()
    var idOfDocument = ""


    private var layoutManager: RecyclerView.LayoutManager? = null


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
        setContentView(R.layout.activity_order_list)

        var authentication: FirebaseAuth? = FirebaseAuth.getInstance()

        nav_view.selectedItemId = R.id.navigation_orderlist
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

        //-------------------------------------------

        var userThatIsLoggedIn: String? = null
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (authentication!!.currentUser != null) {
            if (authentication!!.currentUser!!.email!!.contains("hotmail.com", ignoreCase = true)) {

                //println("nu komt de gwn email")
                println(authentication!!.currentUser!!.email)
                println("in de eerste if")
                userThatIsLoggedIn = authentication!!.currentUser!!.email


            }
        } else {

            //println(authentication!!.currentUser!!.email)
            println("in de else")
            userThatIsLoggedIn = acct!!.email

        }



        db.collection("users").whereEqualTo("email", userThatIsLoggedIn)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    println("dit is de id")
                    println(document.id)
                    println("-----------------------------------")
                    idOfDocument = document.id

                    allOrders.add(document.toObject(Order::class.java))

                }
                layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
                val adapter = OrderListAdapter(allOrders, this)
                recyclerViewOrderList.layoutManager = layoutManager
                recyclerViewOrderList.adapter = adapter
                //authentication!!.signOut()

            }
            .addOnFailureListener { exception ->
                println("error getting documents")
            }

        //------------------------------------


    }
}
