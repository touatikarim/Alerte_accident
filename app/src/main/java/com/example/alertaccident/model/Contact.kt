package com.example.alertaccident.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.StringBuilder


@Entity(tableName = "contacts")
class Contact {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name="id")
    var id:String=""

    @ColumnInfo(name="name")
    var name:String?=null

    @ColumnInfo(name="email")
    var email:String?=null

    @ColumnInfo(name="Phone Number")
    var Phone_Number:String?=null

    constructor(){}

    override fun toString(): String {
        return StringBuilder(name)
            .append("\n")
            .append(Phone_Number)
            .toString()
    }
}