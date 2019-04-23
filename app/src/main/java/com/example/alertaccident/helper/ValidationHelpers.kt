package com.example.alertaccident.helper

import android.text.TextUtils
import android.util.Patterns
import org.w3c.dom.Text
import java.util.regex.Pattern

const val MIN_CREDENTIAL_LENGTH = 8



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

fun isCINValid(CIN:String):Boolean{
    return CIN.length== MIN_CREDENTIAL_LENGTH

}
fun isPhoneValid(phone: String):Boolean {

    return phone.length == MIN_CREDENTIAL_LENGTH
}

fun arePasswordsSame(password: String, repeatPassword: String):Boolean {
    return isPasswordValid(password) && isPasswordValid(repeatPassword) && password == repeatPassword
}

fun isDataValid(email:String,password:String):Int {
    if(TextUtils.isEmpty(email))
        return 0
    else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return 1
    else if (TextUtils.isEmpty(password))
        return 2
    else if (!isPasswordValid(password))
        return 3
    else
        return -1
}
fun isRegistrationValid(email:String,password: String,repeatPassword: String,username:String,phone:String,CIN: String):Int
{
    if (TextUtils.isEmpty(username))
        return 0
    if (!isPhoneValid(phone))
        return 1
    if(!isCINValid(CIN))
        return 2
    if(TextUtils.isEmpty(email))
        return 3
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return 4
    if(TextUtils.isEmpty(password))
        return 5
    if (!isPasswordValid(password))
        return 6
    if (!arePasswordsSame(password, repeatPassword))
        return 7
    else
        return -1

}

fun isUpdateValid(telephone:String,name:String):Int {
    if (TextUtils.isEmpty(name))
        return 0
    if (TextUtils.isEmpty(telephone))
        return 1
    else
        return -1
}
