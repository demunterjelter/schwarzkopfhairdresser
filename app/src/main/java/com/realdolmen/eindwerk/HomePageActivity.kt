package com.realdolmen.eindwerk

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.realdolmen.eindwerk.Fragments.HomeFragment
import com.realdolmen.eindwerk.data.Common
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity() {


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


    override fun onStart() {
        super.onStart()
        val salonAddressHome = findViewById<TextView>(R.id.txt_salon_address_home)
        val salonName = findViewById<TextView>(R.id.txt_salon_barber)
        val time = findViewById<TextView>(R.id.txt_time)

        if (Common.SHOW_BOOKING == 1)
        {
            salonAddressHome.setText(Common.HOME_ADDRESS)
            salonName.setText(Common.HOME_NAME)
            time.setText(Common.DURATION)
            card_booking_info.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val fragment = null;

        nav_view.selectedItemId = R.id.navigation_home
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




}
