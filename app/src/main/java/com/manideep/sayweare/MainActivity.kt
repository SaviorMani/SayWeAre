package com.manideep.sayweare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.manideep.sayweare.ui.theme.SayWeAreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SayWeAreTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLogin = { navController.navigate("home") },
                onRegister = { navController.navigate("register") },
                onForgotPassword = { navController.navigate("forgot") }
            )
        }
        composable("register") {
            RegisterScreen(
                onVerify = { navController.navigate("otp") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("otp") {
            OtpVerificationScreen(
                onBothVerified = { navController.navigate("set_password") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("set_password") {
            SetPasswordScreen(
                onRegistered = { navController.navigate("login") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("forgot") {
            ForgotPasswordScreen(onDone = { navController.popBackStack() })
        }
        composable("home") {
            HomeScreen(onShowAlarm = { navController.navigate("alarm_ui") })
        }
        composable("alarm_ui") {
            AlarmUIScreen()
        }
    }
}

@Composable
private fun ScreenTitle(text: String) {
    Text(
        text = text,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    )
}

@Composable
private fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onForgotPassword: () -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SayWeAre", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = password.value, onValueChange = { password.value = it }, label = { Text("Password") })
        Spacer(Modifier.height(8.dp))
        Button(onClick = onForgotPassword, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
            Text("Forgot password")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onLogin) { Text("Login") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onRegister) { Text("Register") }
    }
}

@Composable
private fun RegisterScreen(
    onVerify: () -> Unit,
    onBack: () -> Unit
) {
    val fullName = remember { mutableStateOf("") }
    val userId = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SayWeAre", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = fullName.value, onValueChange = { fullName.value = it }, label = { Text("Full name") })
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = userId.value, onValueChange = { userId.value = it }, label = { Text("User ID") })
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = phone.value, onValueChange = { phone.value = it }, label = { Text("Phone (+Country code)") })
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email address") })
        Spacer(Modifier.height(16.dp))
        Button(onClick = onVerify) { Text("Verify Mail and Phone") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)) { Text("Back") }
    }
}

@Composable
private fun OtpVerificationScreen(
    onBothVerified: () -> Unit,
    onBack: () -> Unit
) {
    val mobileOtp = remember { mutableStateOf("") }
    val emailOtp = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SayWeAre", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = mobileOtp.value, onValueChange = { mobileOtp.value = it }, label = { Text("Mobile OTP") })
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* verify mobile */ }) { Text("Verify") }
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = emailOtp.value, onValueChange = { emailOtp.value = it }, label = { Text("E-Mail OTP") })
        Spacer(Modifier.height(8.dp))
        Button(onClick = { /* verify email */ }) { Text("Verify") }
        Spacer(Modifier.height(16.dp))
        Button(onClick = onBothVerified) { Text("Continue") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)) { Text("Back") }
    }
}

@Composable
private fun SetPasswordScreen(
    onRegistered: () -> Unit,
    onBack: () -> Unit
) {
    val password = remember { mutableStateOf("") }
    val confirm = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Set Password", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = password.value, onValueChange = { password.value = it }, label = { Text("New password") })
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = confirm.value, onValueChange = { confirm.value = it }, label = { Text("Re-enter password") })
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRegistered) { Text("Register") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack, colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)) { Text("Back") }
    }
}

@Composable
private fun ForgotPasswordScreen(onDone: () -> Unit) {
    val identifier = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Forgot Password", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(value = identifier.value, onValueChange = { identifier.value = it }, label = { Text("Email or Phone") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = onDone) { Text("Send OTP") }
    }
}

@Composable
private fun HomeScreen(onShowAlarm: () -> Unit) {
    val context = androidx.compose.ui.platform.LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Emergency Home", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        Button(onClick = { com.manideep.sayweare.notifications.AlarmNotifier.showAlarm(context) }) { Text("Preview Alarm Notification") }
    }
}

@Composable
private fun AlarmUIScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("SayWeAre", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text("It's an Emergency", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { /* claim */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) { Text("I'm the Savior") }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = { /* decline */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
        ) { Text("I'm Sorry") }
    }
}