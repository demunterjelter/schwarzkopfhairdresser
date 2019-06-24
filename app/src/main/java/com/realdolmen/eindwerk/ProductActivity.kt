package com.realdolmen.eindwerk

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.realdolmen.eindwerk.adapter.ProductListAdapter
import com.realdolmen.eindwerk.data.Product
import kotlinx.android.synthetic.main.activity_product.*


class ProductActivity : AppCompatActivity() {

    private var adapter: ProductListAdapter? = null
    private var productListCrds: ArrayList<Product>? = null
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
        setContentView(R.layout.activity_product)


        productListCrds = ArrayList<Product>()
        layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        adapter = ProductListAdapter(productListCrds!!, this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


        var imageIdList = arrayOf<Int>(
            R.drawable.instant_blush,
            R.drawable.blush_wash,
            R.drawable.keratin_restore_mask,
            R.drawable.keratin_restore_shampoo,
            R.drawable.threed_men_hair_shampoo,
            R.drawable.threed_men_molding_wax,
            R.drawable.threed_men_strong_hold_gel,
            R.drawable.treatment,
            R.drawable.vibrant_red_micellar_shampoo,
            R.drawable.xxl_rich_micellar_shampoo,
            R.drawable.repair_rescue_micellar_shampoo,
            R.drawable.repair_rescue_treatment,
            R.drawable.repair_rescue_xxl_sealed_ends,
            R.drawable.moisture_kick_micellar_cleansing_conditioner,
            R.drawable.moisture_kick_micellar_shampoo,
            R.drawable.moisture_kick_treatment
        )

        var productNameList: Array<String> = arrayOf(
            "instant blush",
            "blush wash",
            "keratin restore bonding mix",
            "keratin restore bonding shampoo",
            "hair & body shampoo",
            "molding wax",
            "strong hold gel",
            "treatment",
            "vibrant red micellar shampoo",
            "XXL rich micellar shampoo",
            "micellar shampoo",
            "treatment",
            "xxl sealed ends",
            "micellar cleansing conditioner",
            "micellar shampoo",
            "treatment"
        )

        var productVolumeList: Array<String> = arrayOf(
            "250ml",
            "250ml",
            "200ml",
            "250ml",
            "250ml",
            "100ml",
            "150ml",
            "200ml",
            "250ml",
            "500ml",
            "250ml",
            "200ml",
            "150ml",
            "500ml",
            "250ml",
            "200ml"
        )


        var moneyProductList: Array<Double> = arrayOf(
            20.95,
            20.95,
            27.85,
            38.95,
            15.50,
            17.50,
            17.50,
            21.50,
            15.95,
            22.50,
            15.95,
            21.50,
            25.50,
            27.50,
            15.95,
            21.50
        )

        for (i in 0..14) {
            var product = Product()
            product.productName = productNameList[i]
            product.volume = productVolumeList[i]
            product.amount = moneyProductList[i]
            product.img = imageIdList[i]
            productListCrds?.add(product)
        }
        adapter!!.notifyDataSetChanged()

        recyclerView.setOnClickListener { }



        nav_view.selectedItemId = R.id.navigation_products
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