package com.example.alertaccident.ui




import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import com.example.alertaccident.R
import com.example.alertaccident.ui.login.SignIn


class Connexion : AppCompatActivity(){

    private var locationManager: LocationManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (!isLocationEnabled)
        { showAlert()}

    }

    private fun showAlert() {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(this.getString(R.string.location))
            .setMessage(this.getString(R.string.Gpsmsg))
            .setPositiveButton(this.getString(R.string.pos_settings)) { paramDialogInterface, paramInt ->
                val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(myIntent)
            }
            .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
        dialog.show()
    }

    override fun onBackPressed() {
        tellFragments()
        super.onBackPressed()
    }

    private fun tellFragments() {
        val fragments = supportFragmentManager.fragments
        for (f in fragments) {
            if (f != null && f is SignIn)
                f.onBackPressed()
        }
    }
    private val isLocationEnabled: Boolean
        get() {
            locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }
}

