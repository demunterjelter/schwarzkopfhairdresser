package com.realdolmen.eindwerk.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.realdolmen.eindwerk.OrderListActivity
import com.realdolmen.eindwerk.R
import com.realdolmen.eindwerk.data.Order

class OrderListAdapter(private val list: ArrayList<Order>, private val context: Context) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(order: Order) {

            //var id:  TextView = itemView.findViewById(R.id.tv_email_amount) as TextView
            var email: TextView = itemView.findViewById(R.id.tv_email_amount) as TextView
            var firstname: TextView = itemView.findViewById(R.id.tv_firstname_orderList_amount) as TextView
            var lastname: TextView = itemView.findViewById(R.id.tv_lastname_orderList_amount) as TextView
            var instantBlushAmount: TextView = itemView.findViewById(R.id.tv_instant_blush_amount) as TextView
            var blushWash: TextView = itemView.findViewById(R.id.tv_blush_wash_amount) as TextView
            var keratinRestoreMask: TextView = itemView.findViewById(R.id.tv_keratin_restore_mask_amount) as TextView
            var keratinRestoreShampoo: TextView =
                itemView.findViewById(R.id.tv_keratin_restore_shampoo_amount) as TextView
            var threedMenHairShampoo: TextView =
                itemView.findViewById(R.id.tv_threed_men_hair_shampoo_amount) as TextView
            var threedMenMoldingWax: TextView = itemView.findViewById(R.id.tv_threed_men_molding_wax_amount) as TextView
            var threedMenStrongHoldGel: TextView =
                itemView.findViewById(R.id.tv_threed_men_strong_hold_gel_amount) as TextView
            var treatment: TextView = itemView.findViewById(R.id.tv_treatment_amount) as TextView
            var vibrantRedMicellarShampoo: TextView =
                itemView.findViewById(R.id.tv_vibrant_red_micellar_shampoo_amount) as TextView
            var xxlRichMicellarShampoo: TextView =
                itemView.findViewById(R.id.tv_xxl_rich_micellar_shampoo_amount) as TextView
            var repairRescueMicellarShampoo: TextView =
                itemView.findViewById(R.id.tv_repair_rescue_micellar_shampoo_amount) as TextView
            var repairRescueTreatment: TextView =
                itemView.findViewById(R.id.tv_repair_rescue_treatment_amount) as TextView
            var repairRescueXxlSealedEnds: TextView =
                itemView.findViewById(R.id.tv_repair_rescue_xxl_sealed_ends_amount) as TextView
            var moistureKickMicellarCleansingConditioner: TextView =
                itemView.findViewById(R.id.tv_moisture_kick_micellar_cleansing_conditioner_amount) as TextView
            var moistureKickMicellarShampoo: TextView =
                itemView.findViewById(R.id.tv_moisture_kick_micellar_shampoo_amount) as TextView
            var moistureKickTreatment: TextView =
                itemView.findViewById(R.id.tv_moisture_kick_treatment_amount) as TextView

            //id.text = order.id.toString()
            email.text = order.email
            firstname.text = order.firstname
            lastname.text = order.lastname
            instantBlushAmount.text = order.instantBlushAmount
            blushWash.text = order.blushWash
            keratinRestoreMask.text = order.keratinRestoreMask
            keratinRestoreShampoo.text = order.keratinRestoreShampoo
            threedMenHairShampoo.text = order.threedMenHairShampoo
            threedMenMoldingWax.text = order.threedMenMoldingWax
            threedMenStrongHoldGel.text = order.threedMenStrongHoldGel
            treatment.text = order.treatment
            vibrantRedMicellarShampoo.text = order.vibrantRedMicellarShampoo
            xxlRichMicellarShampoo.text = order.xxlRichMicellarShampoo
            repairRescueMicellarShampoo.text = order.repairRescueMicellarShampoo
            repairRescueTreatment.text = order.repairRescueTreatment
            repairRescueXxlSealedEnds.text = order.repairRescueXxlSealedEnds
            moistureKickMicellarCleansingConditioner.text = order.moistureKickMicellarCleansingConditioner
            moistureKickMicellarShampoo.text = order.moistureKickMicellarShampoo
            moistureKickTreatment.text = order.moistureKickTreatment

            val db = FirebaseFirestore.getInstance()
            val collection = db.collection("users")

            val thrashButton: Button = itemView.findViewById(R.id.btn_delete) as Button

            thrashButton.setOnClickListener {
                val deleteAlert = AlertDialog.Builder(context)
                deleteAlert.setTitle("Delete object")
                deleteAlert.setMessage(
                    "Are you sure you want to delete this order?"
                )
                deleteAlert.setPositiveButton("Delete") { dialogInterface: DialogInterface, i: Int ->
                    collection.document("${order.firstname}").delete()
                    val intent = Intent(context, OrderListActivity::class.java)
                    context.startActivity(intent)
                }

                deleteAlert.setNegativeButton("Cancel") { dialogInterface: DialogInterface, i: Int ->
                }

                val alertDialog: AlertDialog = deleteAlert.create()
                alertDialog.show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getColor(R.color.colorAccent))
                }

                val textView = alertDialog.findViewById<TextView>(android.R.id.message)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView?.setTextColor(context.getColor(R.color.colorAccent))
                }
                alertDialog.window?.setBackgroundDrawableResource(R.color.colorPrimaryDark)

            }

            //--------------------------------------------------------

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): OrderListAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.order_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrderListAdapter.ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

}