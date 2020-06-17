package com.realdolmen.eindwerk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_home_page.nav_view
import java.util.Arrays.asList as asList1
import android.support.v4.view.ViewPager
import com.realdolmen.eindwerk.adapter.MyViewPagerAdapter
import com.realdolmen.eindwerk.data.Common
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.realdolmen.eindwerk.Common.NonSwipeViewPager
import com.realdolmen.eindwerk.data.Barber
import com.realdolmen.eindwerk.data.Salon
import dmax.dialog.SpotsDialog

class AppointmentActivity : AppCompatActivity() {

    lateinit var dialog: SpotsDialog
    lateinit var barberRef: CollectionReference
    private val buttonNextReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val step = intent.getIntExtra(Common.KEY_STEP,0)

            if (step == 1)
                Common.currentSalon = intent.getParcelableExtra(Common.KEY_SALON_STORE)
            else if (step == 2)
                Common.currentBarber = intent.getParcelableExtra(Common.KEY_BARBER_SELECTED)
            else if (step == 3)
                Common.currentTimeSlot = intent.getIntExtra(Common.KEY_TIME_SLOT, -1)
            btn_next_step.setEnabled(true)
            setColorButton()
        }
    }

private lateinit var viewPager: NonSwipeViewPager//ViewPager
    private lateinit var pagerAdapter: MyViewPagerAdapter



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

     override fun onDestroy() {
         LocalBroadcastManager.getInstance(this).unregisterReceiver(buttonNextReceiver);
        super.onDestroy()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        val button = findViewById<Button>(R.id.btn_next_step)
        button.setOnClickListener {

            if (Common.step < 3 || Common.step == 0)
            {
                Common.step++
                if(Common.step ==1)
                {
                    if (Common.currentSalon != null)
                        loadBarberBySalon(Common.currentSalon.getSalonId())
                }
                else if(Common.step == 2)
                {
                    if (Common.currentBarber != null)
                        loadTimeSlotOfBarber(Common.currentBarber.getBarberId())
                }
                else if(Common.step == 3)
                {
                    if (Common.currentTimeSlot != -1)
                        confirmBooking()
                }
                viewPager.setCurrentItem(Common.step)
            }
        }

        val buttonprev = findViewById<Button>(R.id.btn_previous_step)
        buttonprev.setOnClickListener {
            if (Common.step == 3 || Common.step > 0 )
            {
                Common.step--
                viewPager.setCurrentItem(Common.step)
            }
        }

         dialog = SpotsDialog.Builder().setContext(this).setCancelable(false).build() as SpotsDialog


        LocalBroadcastManager.getInstance(this).registerReceiver(buttonNextReceiver,
            IntentFilter(Common.KEY_ENABLE_BUTTON_NEXT))

        setupStepView()
        setColorButton()



        viewPager = findViewById(R.id.view_pager)
        pagerAdapter = MyViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = pagerAdapter



        viewPager.setOffscreenPageLimit(4)
        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(i: Int) {
                step_view.go(i, true)
                if ( i == 0)

                    btn_previous_step.isEnabled = false;
                else
                    btn_previous_step.isEnabled = true;

                btn_next_step.setEnabled(false)
                setColorButton()
            }

        })


        nav_view.selectedItemId = R.id.navigation_appointment
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

    private fun confirmBooking() {
        val intent = Intent(Common.KEY_CONFIRM_BOOKING)
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(intent)
    }

    private fun loadTimeSlotOfBarber(barberId: String) {
        //stuur local broadcast naar fragment 3
        val intent = Intent(Common.KEY_DISPLAY_TIME_SLOT)
        LocalBroadcastManager.getInstance(baseContext).sendBroadcast(intent)

    }

    private fun loadBarberBySalon(salonId: String) {
        dialog.show()
        //       /AllSalon/Sint-Genesius-Rode/Branch/UxrpDId8pTA63zkdgwpx/Barber
        if (!TextUtils.isEmpty(Common.city))
        {
            barberRef = FirebaseFirestore.getInstance()
                .collection("AllSalon")
                .document(Common.city)
                .collection("Branch")
                .document(salonId)
                .collection("Barber")


            barberRef.get().addOnSuccessListener { result ->
                val barbers = java.util.ArrayList<Barber>()
                for (barberSnapshot in result) {
                     val barber = barberSnapshot.toObject(Barber::class.java)
                    barber.setPassword("")
                    barber.setBarberId(barberSnapshot.getId())
                    barbers.add(barber)
                }

                val intent = Intent(Common.KEY_BARBER_LOAD_DONE)
                intent.putParcelableArrayListExtra(Common.KEY_BARBER_LOAD_DONE,barbers)
                LocalBroadcastManager.getInstance(baseContext).sendBroadcast(intent)

                dialog.dismiss()
            }
                .addOnFailureListener { exception ->
                    Toast.makeText( baseContext,"fout gebeurd", Toast.LENGTH_SHORT).show()
                }

        }

    }

    fun setColorButton() {
        if (btn_next_step.isEnabled()){
            btn_next_step.setBackgroundResource(R.color.colorPrimary)
        }
         else
        {
            btn_next_step.setBackgroundResource(android.R.color.darker_gray)
        }

         if (btn_previous_step.isEnabled()){
             btn_previous_step.setBackgroundResource(R.color.colorPrimary)
         }
         else
         {
             btn_previous_step.setBackgroundResource(android.R.color.darker_gray)
         }
    }

    fun setupStepView() {

        val stepList = ArrayList<String>()
        stepList.add("Salon")
        stepList.add("Barber")
        stepList.add("Time")
        stepList.add("Confirm")

        step_view.setSteps(stepList)
    }


}



