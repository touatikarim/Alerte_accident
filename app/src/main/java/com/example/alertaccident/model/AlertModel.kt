package com.example.alertaccident.model

data class AlertModel(var alert_id:String?,var user_id:String,var desc:String,var service:String,var email:String,var victims:String,var latitude:String,var longitude:String,var imageurl:String?,var videourl:String?,var date:String?,var location:String?){
    constructor():this("","","","","","","","","","","",""){

    }
}