package com.charlie.androidtweaks.shake

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import com.charlie.androidtweaks.data.TAG_ANDROIDTWEAKS
import com.charlie.androidtweaks.ui.TweakActivity
import java.lang.ref.WeakReference
import kotlin.math.abs

class TweakShakeService : Service() {

    private var mSensorManager: SensorManager? = null
    private var mAccelerometerSensor: Sensor? = null

    /**
     * shake range
     */
    private val SHAKE_RANGE = 25f

    private var isShake = false

    private var mHandler: TweakShakeHandler? = null

    companion object {
        private const val MSG_SHAKE_START = 1
        private const val MSG_SHAKE_END = -1
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        mSensorManager?.let {
            mAccelerometerSensor = it.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            if (mAccelerometerSensor != null) {
                it.registerListener(
                    mShakeListener,
                    mAccelerometerSensor!!,
                    SensorManager.SENSOR_DELAY_UI
                )
                mHandler = TweakShakeHandler(this)
            }
        }
        Log.d(TAG_ANDROIDTWEAKS, "shake service start command")
        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * shake listener
     */
    private val mShakeListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent?) {
            event ?: return
            val type = event?.sensor?.type ?: return
            if (type == Sensor.TYPE_ACCELEROMETER) {
                val values = event.values ?: FloatArray(3)
                val x = values[0]
                val y = values[1]
                val z = values[2]

                if ((abs(x) > SHAKE_RANGE || abs(y) > SHAKE_RANGE || abs(z) > SHAKE_RANGE) && !isShake) {
                    isShake = true
                    Thread {
                        mHandler?.sendEmptyMessage(MSG_SHAKE_START)
                        Thread.sleep(500)
                        mHandler?.sendEmptyMessage(MSG_SHAKE_END)
                    }.start()
                }

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        mSensorManager?.unregisterListener(mShakeListener, mAccelerometerSensor)
        mSensorManager = null
        mAccelerometerSensor = null
        mHandler?.removeCallbacksAndMessages(null)
        mHandler = null
        Log.d(TAG_ANDROIDTWEAKS, "shake service stop command")
    }

    /**
     * shake handler
     */
    class TweakShakeHandler(context: Context) : Handler() {

        private var weakReference: WeakReference<Context>? = null

        init {
            weakReference = WeakReference(context)
        }

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            msg ?: return
            val context = weakReference?.get() as? TweakShakeService ?: return
            when (msg.what) {
                MSG_SHAKE_START -> {
                    TweakActivity.start(context)
                }
                MSG_SHAKE_END -> {
                    context.isShake = false
                }
            }

        }
    }

}