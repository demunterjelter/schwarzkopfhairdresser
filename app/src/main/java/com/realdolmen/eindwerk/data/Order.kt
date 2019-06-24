package com.realdolmen.eindwerk.data

import android.os.Parcel
import android.os.Parcelable

class Order() : Parcelable {

   // var id: Long? = null
    var email: String? = null
    var firstname: String? = null
    var lastname: String? = null
    var instantBlushAmount: String? = null
    var blushWash: String? = null
    var keratinRestoreMask: String? = null
    var keratinRestoreShampoo: String? = null
    var threedMenHairShampoo: String? = null
    var threedMenMoldingWax: String? = null
    var threedMenStrongHoldGel: String? = null
    var treatment: String? = null
    var vibrantRedMicellarShampoo: String? = null
    var xxlRichMicellarShampoo: String? = null
    var repairRescueMicellarShampoo: String? = null
    var repairRescueTreatment: String? = null
    var repairRescueXxlSealedEnds: String? = null
    var moistureKickMicellarCleansingConditioner: String? = null
    var moistureKickMicellarShampoo: String? = null
    var moistureKickTreatment: String? = null

    constructor(parcel: Parcel) : this() {
       // id = parcel.readValue(Long::class.java.classLoader) as? Long
        email = parcel.readString()
        firstname = parcel.readString()
        lastname = parcel.readString()
        instantBlushAmount = parcel.readString()
        blushWash = parcel.readString()
        keratinRestoreMask = parcel.readString()
        keratinRestoreShampoo = parcel.readString()
        threedMenHairShampoo = parcel.readString()
        threedMenMoldingWax = parcel.readString()
        threedMenStrongHoldGel = parcel.readString()
        treatment = parcel.readString()
        vibrantRedMicellarShampoo = parcel.readString()
        xxlRichMicellarShampoo = parcel.readString()
        repairRescueMicellarShampoo = parcel.readString()
        repairRescueTreatment = parcel.readString()
        repairRescueXxlSealedEnds = parcel.readString()
        moistureKickMicellarCleansingConditioner = parcel.readString()
        moistureKickMicellarShampoo = parcel.readString()
        moistureKickTreatment = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        //parcel.writeValue(id)
        parcel.writeString(email)
        parcel.writeString(firstname)
        parcel.writeString(lastname)
        parcel.writeString(instantBlushAmount)
        parcel.writeString(blushWash)
        parcel.writeString(keratinRestoreMask)
        parcel.writeString(keratinRestoreShampoo)
        parcel.writeString(threedMenHairShampoo)
        parcel.writeString(threedMenMoldingWax)
        parcel.writeString(threedMenStrongHoldGel)
        parcel.writeString(treatment)
        parcel.writeString(vibrantRedMicellarShampoo)
        parcel.writeString(xxlRichMicellarShampoo)
        parcel.writeString(repairRescueMicellarShampoo)
        parcel.writeString(repairRescueTreatment)
        parcel.writeString(repairRescueXxlSealedEnds)
        parcel.writeString(moistureKickMicellarCleansingConditioner)
        parcel.writeString(moistureKickMicellarShampoo)
        parcel.writeString(moistureKickTreatment)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }

}