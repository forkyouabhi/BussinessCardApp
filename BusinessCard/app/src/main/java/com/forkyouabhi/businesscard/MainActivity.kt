package com.forkyouabhi.businesscard

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.floor

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    private var lightLevel by mutableStateOf("Fetching Light Level...")
    private var diceValue by mutableStateOf(1) // Default dice value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Sensor Manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        setContent {
            DiceRollerApp(lightLevel, diceValue)
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            val lightValue = event.values[0]
            lightLevel = "Light Level: $lightValue"

            // Dice value = (floor(lightLevel) % 6) + 1
            diceValue = ((floor(lightValue) % 6) + 1).toInt()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for now
    }
}

@Composable
fun DiceRollerApp(lightLevel: String, diceValue: Int) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = androidx.compose.ui.graphics.Color(0xFFFFE9E7)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Interactive Dice Roller", fontSize = 24.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = lightLevel, fontSize = 18.sp, color = androidx.compose.ui.graphics.Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            // Display Dice Image
            Image(
                painter = painterResource(id = getDiceImage(diceValue)),
                contentDescription = "Dice Roll",
                modifier = Modifier
                    .size(150.dp)
                    .clickable { } // No need for manual rolling, sensor controls it
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Dice Value: $diceValue", fontSize = 20.sp, color = androidx.compose.ui.graphics.Color.Black)
        }
    }
}

// Function to return dice image based on the value
fun getDiceImage(value: Int): Int {
    return when (value) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDiceRollerApp() {
    DiceRollerApp("Light Level: 8.0", 3)
}
