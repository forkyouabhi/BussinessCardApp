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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private var proximitySensor: Sensor? = null
    private var magnetometerSensor: Sensor? = null

    // Using StateFlow to share sensor data across composables
    private val _lightLevel = mutableStateOf("Fetching Light Level...")
    private val _proximityLevel = mutableStateOf("Fetching Proximity Level...")
    private val _magnetometerStatus = mutableStateOf("Checking Magnetometer...")

    val lightLevel: State<String> = _lightLevel
    val proximityLevel: State<String> = _proximityLevel
    val magnetometerStatus: State<String> = _magnetometerStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        magnetometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        // Check if Magnetometer is available
        _magnetometerStatus.value = if (magnetometerSensor != null) {
            "Magnetometer: Available"
        } else {
            "Magnetometer: Not Available"
        }

        setContent {
            BusinessCardApp(
                lightLevel = lightLevel.value,
                proximityLevel = proximityLevel.value,
                magnetometerStatus = magnetometerStatus.value
            )
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
        proximitySensor?.also {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_LIGHT -> {
                _lightLevel.value = "Light Level: ${event.values[0]}"
            }
            Sensor.TYPE_PROXIMITY -> {
                _proximityLevel.value = "Proximity: ${event.values[0]}"
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed for now
    }
}

@Composable
fun BusinessCardApp(
    lightLevel: String,
    proximityLevel: String,
    magnetometerStatus: String
) {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFE9E7)
    ) {
        NavHost(navController = navController, startDestination = "business_card") {
            composable("business_card") {
                BusinessCardScreen(
                    navController = navController
                )
            }
            composable("sensors") {
                SensorScreen(
                    navController = navController,
                    lightLevel = lightLevel,
                    proximityLevel = proximityLevel,
                    magnetometerStatus = magnetometerStatus
                )
            }
        }
    }
}

@Composable
fun BusinessCardScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.85f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFfedbca))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_picture),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                )

                Text(
                    text = "John Doe",
                    fontSize = 22.sp,
                    color = Color.Black
                )
                Text(
                    text = "Android Developer",
                    fontSize = 16.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hello Thunder Bay",
                    fontSize = 18.sp,
                    color = Color(0xFF331102)
                )

                HorizontalDivider(thickness = 1.dp, color = Color.Gray)

                ContactInfo()

                Spacer(modifier = Modifier.height(16.dp))

                // Button to navigate to sensors page
                Button(
                    onClick = { navController.navigate("sensors") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF331102)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Sensors",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "View Sensor Data", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun SensorScreen(
    navController: NavController,
    lightLevel: String,
    proximityLevel: String,
    magnetometerStatus: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.85f),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFfedbca))
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Sensor Information",
                    fontSize = 22.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display Sensor Data
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = lightLevel, fontSize = 16.sp, color = Color.Black)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = proximityLevel, fontSize = 16.sp, color = Color.Black)
                    }
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = magnetometerStatus, fontSize = 16.sp, color = Color.Black)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Button to navigate back
                Button(
                    onClick = { navController.popBackStack() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF331102)
                    )
                ) {
                    Text(text = "Back to Business Card", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ContactInfo() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        ContactItem(Icons.Default.Phone, "123-456-7890")
        ContactItem(Icons.Default.Email, "info@johndoe.app")
        ContactItem(Icons.Default.AccountCircle, "@forkyouabhi")
    }
}

@Composable
fun ContactItem(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF331102),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, color = Color.Black)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBusinessCard() {
    BusinessCardApp(
        lightLevel = "Light Level: 100.0",
        proximityLevel = "Proximity: Near",
        magnetometerStatus = "Magnetometer: Available"
    )
}