package com.example.alertaccident.model

data class ReportModel (var nomA:String, var prenomA:String,var adresseA:String,var couleurA:String,var marqueA:String,var assuranceA:String,var nomB:String,
                        var prenomB:String,var adresseB:String,var couleurB:String,var marqueB:String,
                        var assuranceB:String,var numeroPermisB:String,var localisation:String,var date:String,var user_id:String){
    constructor():this("","","","","","","","","","","","","",""
    ,"",""){

    }
}









