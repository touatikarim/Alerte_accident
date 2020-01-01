package com.example.alertaccident.Service

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class CrashListener(val context:Context):SensorEventListener {

    companion object {
        const val FORCE_THRESHOLD = 10000
        const val TIME_THRESHOLD = 75
        const val SHAKE_TIMEOUT = 500
        const val SHAKE_DURATION = 150
        const val SHAKE_COUNT = 1
    }
    private var mContext: Context = context
    var mSensorMgr: SensorManager? = null
    var mLastX=-1.0f
    var mLastY=-1.0f
    var mLastZ=-1.0f
    var mLastTime: Long = 0
    var mShakeCount = 0
    var mLastShake: Long = 0
    var mLastForce: Long = 0
    var mCrashListener: OnCrashListener? = null

    init {
        mSensorMgr = mContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (mSensorMgr == null) {
            Log.d("here", "sensor error")
            throw UnsupportedOperationException("Sensors not supported")
        }
        val supported = mSensorMgr!!.registerListener(this@CrashListener, mSensorMgr!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME)
        if (!supported) {
            mSensorMgr!!.unregisterListener(this, mSensorMgr!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER))
            throw UnsupportedOperationException("Accelerometer not supported")
            Log.d("here", "Accelerometer not supported")
        }
    }



    interface OnCrashListener {
        fun onShake()
    }

    fun setOnCrashListener(listener: OnCrashListener) {
        mCrashListener = listener
    }


    fun resume() {
        if(mSensorMgr != null) {
            mSensorMgr!!.registerListener(this, mSensorMgr!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME)
        }
    }
    fun pause() {
        if(mSensorMgr != null) {
            mSensorMgr!!.unregisterListener(this, mSensorMgr!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER))
        }
    }


    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        if(event?.sensor!!.type != Sensor.TYPE_ACCELEROMETER) return
        val now = System.currentTimeMillis()
        if ((now - mLastForce) > SHAKE_TIMEOUT) {
            mShakeCount = 0
        }
        if ((now - mLastTime) > TIME_THRESHOLD) {
            val diff = now - mLastTime
            val speed =
                Math.abs(event.values[0] +
                        event.values[1] +
                        event.values[2] -
                        mLastX - mLastY - mLastZ) / diff * 10000
            if (speed > FORCE_THRESHOLD) {
                if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                    mLastShake = now
                    mShakeCount = 0
                    if (mCrashListener != null) {
                        mCrashListener!!.onShake()
                    }
                }
                mLastForce = now
            }
            mLastTime = now
            mLastX = event.values[0]
            mLastY = event.values[1]
            mLastZ = event.values[2]
        }
    }



}