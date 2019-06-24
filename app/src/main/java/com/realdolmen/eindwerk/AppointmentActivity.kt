package com.realdolmen.eindwerk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_appointment.*
import kotlinx.android.synthetic.main.activity_home_page.nav_view
import java.util.Arrays.asList as asList1


class AppointmentActivity : AppCompatActivity() {

    private var myWebView: WebView? = null

    private val url = "https://calendar.google.com/calendar?cid=Y2F0aHl2YW5kZXJndWNodEBnbWFpbC5jb20"


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

    override fun onBackPressed() {
        if (myWebView!!.canGoBack()) {
            myWebView!!.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        myWebView = web_view //(WebView)findViewById(R.id.web_view)
        var webSettings = myWebView!!.settings
        webSettings.javaScriptEnabled = true
        myWebView!!.loadUrl(url)

        myWebView!!.setWebViewClient(WebViewClient())


        webSettings.setAppCacheEnabled(true)
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.setAppCachePath(cacheDir.path)

        webSettings.setSupportZoom(true)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = true


        webSettings.textZoom = 125

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
}
