package com.example.ferrykepri.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Anchor
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsBoat
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ferrykepri.data.Validators
import com.example.ferrykepri.ui.components.WaveBottom
import com.example.ferrykepri.ui.theme.FerryBlue500
import com.example.ferrykepri.ui.theme.FerryBlue700
import com.example.ferrykepri.ui.theme.FerryBlue800
import com.example.ferrykepri.ui.theme.FerryBorder
import com.example.ferrykepri.ui.theme.FerryGreen
import com.example.ferrykepri.ui.theme.FerryMuted
import com.example.ferrykepri.ui.theme.FerryNavy
import com.example.ferrykepri.ui.theme.FerrySky50
import com.example.ferrykepri.ui.theme.FerryWhite
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    fun showMessage(message: String) {
        scope.launch { snackbarHostState.showSnackbar(message) }
    }

    fun submit() {
        val validation = Validators.login(email, password)
        if (validation != null) {
            if (validation.startsWith("Email") || validation.startsWith("Format")) {
                emailError = validation
            } else {
                passwordError = validation
            }
            showMessage(validation)
            return
        }
        if (!Validators.isDummyAccount(email, password)) {
            showMessage("Email atau kata sandi salah")
            return
        }
        focusManager.clearFocus()
        onLoginSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(FerrySky50)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .navigationBarsPadding()
        ) {
            // ---------- Header biru ----------
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.verticalGradient(listOf(FerryBlue800, FerryBlue500)))
            ) {
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 42.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = FerryWhite.copy(alpha = 0.18f),
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { showMessage("Fitur demo") }
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Tutup",
                                    tint = FerryWhite,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        LanguageSwitch()
                    }

                    Spacer(Modifier.height(18.dp))

                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = FerryWhite,
                        shadowElevation = 6.dp,
                        modifier = Modifier.size(84.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Filled.DirectionsBoat,
                                contentDescription = null,
                                tint = FerryBlue700,
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(14.dp))
                    Text(
                        text = "FerryKepri",
                        style = MaterialTheme.typography.displaySmall,
                        color = FerryWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Selamat Datang",
                        style = MaterialTheme.typography.headlineSmall,
                        color = FerryWhite
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Masuk untuk kelola jadwal & tiket ferry\nBatam–Singapura",
                        style = MaterialTheme.typography.bodyMedium,
                        color = FerryWhite.copy(alpha = 0.88f),
                        textAlign = TextAlign.Center
                    )
                }
                WaveBottom(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(22.dp)
                )
            }

            // ---------- Kartu form ----------
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-18).dp),
                shape = RoundedCornerShape(24.dp),
                color = FerryWhite,
                shadowElevation = 8.dp
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = "Masuk ke Akun Anda",
                        style = MaterialTheme.typography.headlineSmall,
                        color = FerryNavy
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Gunakan akun terdaftar untuk akses tiket instan & boarding pass",
                        style = MaterialTheme.typography.bodySmall,
                        color = FerryMuted
                    )

                    Spacer(Modifier.height(18.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email / Alamat Surel") },
                        placeholder = { Text("nama@email.com") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Email,
                                contentDescription = null,
                                tint = FerryMuted
                            )
                        },
                        trailingIcon = if (emailError != null) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.ErrorOutline,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        } else null,
                        isError = emailError != null,
                        supportingText = emailError?.let { message -> { Text(message) } },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                            imeAction = ImeAction.Next
                        )
                    )

                    Spacer(Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Kata Sandi",
                            style = MaterialTheme.typography.labelMedium,
                            color = FerryNavy
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            text = "Lupa Kata Sandi?",
                            style = MaterialTheme.typography.labelMedium,
                            color = FerryBlue700,
                            modifier = Modifier.clickable { showMessage("Fitur demo") }
                        )
                    }

                    Spacer(Modifier.height(6.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = null
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Masukkan kata sandi") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = null,
                                tint = FerryMuted
                            )
                        },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) {
                                        Icons.Filled.VisibilityOff
                                    } else {
                                        Icons.Filled.Visibility
                                    },
                                    contentDescription = if (passwordVisible) {
                                        "Sembunyikan kata sandi"
                                    } else {
                                        "Tampilkan kata sandi"
                                    },
                                    tint = FerryMuted
                                )
                            }
                        },
                        isError = passwordError != null,
                        supportingText = passwordError?.let { message -> { Text(message) } },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        visualTransformation = if (passwordVisible) {
                            VisualTransformation.None
                        } else {
                            PasswordVisualTransformation()
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(onDone = { submit() })
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = { submit() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FerryBlue700,
                            contentColor = FerryWhite
                        )
                    ) {
                        Text("Masuk", style = MaterialTheme.typography.labelLarge)
                        Spacer(Modifier.width(8.dp))
                        Text("→", fontSize = 18.sp)
                    }

                    Spacer(Modifier.height(18.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        HorizontalDivider(Modifier.weight(1f), color = FerryBorder)
                        Text(
                            text = "atau masuk dengan",
                            style = MaterialTheme.typography.bodySmall,
                            color = FerryMuted,
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                        HorizontalDivider(Modifier.weight(1f), color = FerryBorder)
                    }

                    Spacer(Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        SocialButton(
                            modifier = Modifier.weight(1f),
                            label = "Google",
                            onClick = { showMessage("Fitur demo") }
                        ) {
                            Text(
                                text = "G",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4285F4),
                                fontSize = 18.sp
                            )
                        }
                        SocialButton(
                            modifier = Modifier.weight(1f),
                            label = "Singpass",
                            onClick = { showMessage("Fitur demo") }
                        ) {
                            Surface(shape = CircleShape, color = Color(0xFFD32F2F)) {
                                Text(
                                    text = "SP",
                                    color = FerryWhite,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Belum punya akun? ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = FerryMuted
                        )
                        Text(
                            text = "Daftar",
                            style = MaterialTheme.typography.bodyMedium,
                            color = FerryBlue700,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { showMessage("Fitur demo") }
                        )
                    }
                }
            }

            // ---------- Footer ----------
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = FerryGreen,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Koneksi terenkripsi & data terlindungi",
                        style = MaterialTheme.typography.labelMedium,
                        color = FerryGreen
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Sesuai protokol Ditjen Imigrasi RI & Maritime and Port Authority of Singapore (MPA)",
                    style = MaterialTheme.typography.bodySmall,
                    color = FerryMuted,
                    textAlign = TextAlign.Center
                )
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(16.dp)
        )
    }
}

@Composable
private fun LanguageSwitch() {
    Surface(
        shape = CircleShape,
        color = FerryWhite.copy(alpha = 0.18f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Anchor,
                contentDescription = null,
                tint = FerryWhite,
                modifier = Modifier.size(14.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "ID",
                style = MaterialTheme.typography.labelMedium,
                color = FerryWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "  /  ",
                style = MaterialTheme.typography.labelSmall,
                color = FerryWhite.copy(alpha = 0.6f)
            )
            Text(
                text = "SG",
                style = MaterialTheme.typography.labelMedium,
                color = FerryWhite.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun SocialButton(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(46.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, FerryBorder)
    ) {
        icon()
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = FerryNavy
        )
    }
}
