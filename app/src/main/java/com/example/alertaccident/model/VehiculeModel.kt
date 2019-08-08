package com.example.alertaccident.model

data class VehiculeModel (var marque:String,var immatricule:String,var assurance:String,var couleur:String,var nombrePortes:String,var id:String,var images:String?){
    constructor():this("","","","","","",""){

    }
}
