package com.example.alertaccident.Helper

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

fun isPasswordValid(password: String): Boolean{
    val expression  ="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&]).{8,}"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(password)
    return matcher.matches()
}
fun isDataValid(email:String,password:String):Int {
    if(TextUtils.isEmpty(email))
        return 0
    else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return 1
    else if (!isPasswordValid(password))
        return 2
    else
        return -1
}