package com.example.alertaccident.model

data class GuestAlertModel (var alert_id:String?,var desc:String,var service:String,var victims:String,var latitude:String?,var longitude:String?,var date:String?,var location:String?,var imageurl:String?){
    constructor():this("","","","","","","","",""){

    }
}