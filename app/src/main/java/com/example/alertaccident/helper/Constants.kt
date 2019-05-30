package com.example.alertaccident.helper

import androidx.navigation.navOptions
import com.example.alertaccident.R
import com.example.alertaccident.model.Contact

object Constants {
    val APP_NAME="Alert Accident"
    val baseurl="http://internship.mobelite.fr:3000/"
    val clientid="310950136179-bnmgleuh3vquqf6kkie01d65su4c5nm8.apps.googleusercontent.com"
    val placeurl="https://maps.googleapis.com/maps/api/place/nearbysearch/"

    val list_of_services= arrayListOf("Police secours","Protection civile Tunis","SAMU","Ambulances médicalisées","Centre Anti-poisons","SOS Médecins","SOS Ambulances",
        "Urgence Le Secours")
    val services= listOf(
        Contact(id="1",name="Protection civile Tunis",email="",Phone_Number = "198"),
        Contact(id="2",name="Police secours",email="",Phone_Number = "197"),
        Contact(id="3",name="SAMU",email="",Phone_Number = "190"),
        Contact(id="4",name="Ambulances médicalisées",email="",Phone_Number = "71780000"),
        Contact(id="5",name="Centre Anti-poisons",email="",Phone_Number = "71335500"),
        Contact(id="6",name="SOS Médecins",email="",Phone_Number = "71744215"),
        Contact(id="7",name="SOS Ambulances",email="",Phone_Number = "71725555"),
        Contact(id="8",name="SAMU",email="",Phone_Number = "190"),
        Contact(id="9",name="Urgence Le Secours",email="",Phone_Number = "71351500")

    )
    var list_of_items = arrayListOf("police","doctor","fire_station", "hospital","pharmacy","insurance_agency", "local_government_office",
          "physiotherapist","car_repair","dentist")
    val max_value=20
    val min_value=1
    val request_interval:Long=5000
    val request_fastest_interval:Long=3000
    val request_smallestDisplacement=10f
    const val permission_code=1000
    const val REQUEST_IMAGE_CAPTURE=1
    const val REQUEST_VIDEO_CAPTURE = 2
    const val REQUEST_PHONE_CALL=1
    val NOTIF_CHANNEL_ID="95"
    val NOTIFICATION_ID=90
    val socialType1= "Google"
    val socialType2="Facebook"
    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
}