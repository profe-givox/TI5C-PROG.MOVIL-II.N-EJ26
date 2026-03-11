package net.ivanvega.missensoresconcompose

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import dev.ricknout.composesensors.accelerometer.getAccelerometerSensor
import dev.ricknout.composesensors.accelerometer.isAccelerometerSensorAvailable
import dev.ricknout.composesensors.accelerometer.rememberAccelerometerSensorValueAsState
import net.ivanvega.missensoresconcompose.ui.theme.MisSensoresConComposeTheme

class MainActivity : ComponentActivity(), SensorEventListener {
    private var mLight: Sensor? = null
    private var mSensor: Sensor? = null
    var magnaetometer: Sensor? = null
    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MisSensoresConComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting2(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val lsss = sensorManager.getSensorList(Sensor.TYPE_ALL)
        lsss.forEach {
            Log.d("sensores", "Vendor ${it.vendor}  name ${it.name}  " +
                    "resolution ${it.resolution}  power ${it.power}")

        }
        magnaetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        if ( magnaetometer != null) {
            // Success! There's a magnetometer.
            Log.d("magnotometro", "magnotometro " +
                    "${magnaetometer!!.name}  ${magnaetometer!!.vendor}" +
                    " ${magnaetometer!!.resolution} ${magnaetometer!!.power}")
        } else {
            // Failure! No magnetometer.
            Log.d("magnotometro", "no magnetometro")
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY) != null) {
            val gravSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_GRAVITY)
            // Use the version 3 gravity sensor.
            mSensor = gravSensors.firstOrNull { it.vendor.contains("Google LLC") && it.version == 3 }
        }
        if (mSensor == null) {
            // Use the accelerometer.
            mSensor = if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {

                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
            } else {
                // Sorry, there are no accelerometers on your device.
                // You can't play this game.
                Log.d("sensores", "no hay acelerometro")
                null

            }

        }else{
            Log.d("sensores", "hay acelerometro")
        }

        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    }

    override fun onResume() {
        super.onResume()

            sensorManager.registerListener(this,
                mSensor, SensorManager.SENSOR_DELAY_NORMAL)


    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // The light sensor returns a single value.
        // Many sensors return 3 values, one for each axis.
        //val lux = event?.values[0]
        //Log.d("sensores Lux", "onSensorChanged $lux")
        Log.d("sensores acelerome", "onSensorChanged x=${event!!.values[0]}" +
                ", y=${event!!.values[1]}, z=${event!!.values[2]}")



        // Do something with this sensor value.
    }
}


@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    // Check if accelerometer sensor is available
    val available = isAccelerometerSensorAvailable()

// Get accelerometer sensor
    val sensor = getAccelerometerSensor()

// Remember accelerometer sensor value as State that updates as SensorEvents arrive
    val sensorValue by rememberAccelerometerSensorValueAsState()
// Accelerometer sensor values. Also available: sensorValue.timestamp, sensorValue.accuracy
    val (x, y, z) = sensorValue.value

    Text(
        text = "Hello $name! x=  ${x} y=${y} z=${z}   ",
        modifier = modifier
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val valor = remember { mutableStateOf(0.0f )  }

    val ctx = LocalContext.current
    DisposableEffect(Unit) {

        val sm = ctx.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val s = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val ltn = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }

            override fun onSensorChanged(event: SensorEvent?) {
                valor.value = event!!.values[0]
            }

        }

        sm.registerListener( ltn  ,
            s,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        onDispose {
            sm.unregisterListener(ltn)
        }
    }

    Text(
        text = "Hello $name! ${valor.value}",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MisSensoresConComposeTheme {
        Greeting("Android")
    }
}