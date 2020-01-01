package com.example.alertaccident.model

import java.util.*

data class Data (val agenceAssurance : AgenceAssurance,
                 val googleToken:String,
                 val facebookToken:String,
                 val _id : String,
                 val nom : String,
                 val CIN : Int,
                 val email : String,
                 val telephone : Int,
                 val password : String,
                 val __v : Int,
                 val immatricule:String,
                 val marque:String,
                 val assurance:String,
                 val couleur:String,
                 val nombrePortes:String,
                 val images:ArrayList<String>,
                 var nomA:String, var prenomA:String,var adresseA:String,
                 var couleurA:String,var marqueA:String,var assuranceA:String,var nomB:String,
                 var prenomB:String,var adresseB:String,var couleurB:String,var marqueB:String,
                 var assuranceB:String,var numeroPermisB:String,var localisation:String,var date:String)
