package com.example.alertaccident.helper

import androidx.navigation.navOptions
import com.example.alertaccident.R
import com.example.alertaccident.model.Contact

object Constants {
    val baseurl="http://internship.mobelite.fr:3000/"
    val clientid="310950136179-bnmgleuh3vquqf6kkie01d65su4c5nm8.apps.googleusercontent.com"
    val list_of_services= arrayListOf("Police secours","Protection civile Tunis","SAMU","Ambulances médicalisées","Centre Anti-poisons","SOS Médecins","SOS Ambulances",
        "Urgence Le Secours")
    val services= listOf<Contact>(
        Contact(id="1",name="Police secours",email="",Phone_Number = "197")
    )
    val max_value=20
    val min_value=1
    val request_interval:Long=5000
    val request_fastest_interval:Long=3000
    val request_smallestDisplacement=10f
    const val permission_code=1000
    const val REQUEST_IMAGE_CAPTURE=1
    const val REQUEST_VIDEO_CAPTURE = 2
    const val REQUEST_PHONE_CALL=1
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