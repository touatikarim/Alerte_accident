package com.example.alertaccident.Helper

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

fun isPasswordValid(password: String?): Boolean{
    val expression  ="^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,}).*\$"
    val expression1 ="^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\\$%\\^&\\*])(?=.{8,}).*\$"
    val expression2="^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\\\$%\\^&\\*])(?=.{8,}).*\$"
    val expression3="^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#\\\$%\\^&\\*])(?=.{8,}).*\$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val pattern1 = Pattern.compile(expression1, Pattern.CASE_INSENSITIVE)
    val pattern2 = Pattern.compile(expression2, Pattern.CASE_INSENSITIVE)
    val pattern3 = Pattern.compile(expression3, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(password)
    val matcher1 = pattern1.matcher(password)
    val matcher2 = pattern2.matcher(password)
    val matcher3 = pattern3.matcher(password)
    return (matcher.matches() || matcher1.matches() || matcher2.matches() || matcher3.matches())
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