package com.example.alertaccident.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alertaccident.model.Contact

@Database(entities = arrayOf(Contact::class),version=1)
abstract class ContactDatabase:RoomDatabase(){
    abstract fun contactDAO():ContactDAO

    companion object {
        val DATABASE_NAME="Alert_accident_database"

        private var mInstance:ContactDatabase?=null

        fun getInstance(context: Context):ContactDatabase{
            if(mInstance==null)
                mInstance= Room.databaseBuilder(context,ContactDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            return mInstance!!
        }
    }
}