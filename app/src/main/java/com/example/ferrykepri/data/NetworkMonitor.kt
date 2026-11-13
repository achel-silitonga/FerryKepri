package com.example.ferrykepri.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Stable
class NetworkStatus internal constructor(
    val isOnline: Boolean,
    private val onRetry: () -> Unit
) {
    fun retry() = onRetry()
}

/**
 * Memantau koneksi internet perangkat. Selain memakai NetworkCallback,
 * status dicek ulang secara berkala agar perubahan (misalnya mode pesawat)
 * selalu terdeteksi meski callback tidak terpanggil.
 */
@Composable
fun rememberNetworkStatus(): NetworkStatus {
    val context = LocalContext.current
    var online by remember { mutableStateOf(context.hasInternet()) }
    var retryKey by remember { mutableIntStateOf(0) }

    DisposableEffect(context, retryKey) {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val handler = Handler(Looper.getMainLooper())
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                online = true
            }

            override fun onLost(network: Network) {
                online = false
            }

            override fun onUnavailable() {
                online = false
            }
        }

        try {
            manager.registerDefaultNetworkCallback(callback, handler)
        } catch (e: Exception) {
            online = context.hasInternet()
        }

        onDispose {
            try {
                manager.unregisterNetworkCallback(callback)
            } catch (_: Exception) {
                // Callback mungkin sudah tidak terdaftar.
            }
        }
    }

    LaunchedEffect(context, retryKey) {
        while (true) {
            online = context.hasInternet()
            delay(2_000)
        }
    }

    return remember(online, retryKey) {
        NetworkStatus(online) { retryKey++ }
    }
}

fun Context.hasInternet(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        ?: return true
    val network = manager.activeNetwork ?: return false
    val capabilities = manager.getNetworkCapabilities(network) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
