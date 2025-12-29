package com.example.academy_tbc.data.manager

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class NetworkStateManager @Inject constructor(
    @param:ApplicationContext private val context: Context,
) {
    private val connectivityManager = context.getSystemService<ConnectivityManager>()!!
    private fun isNetworkEffectivelyConnected(networkCapabilities: NetworkCapabilities?): Boolean {
        if (networkCapabilities == null) return false
        val hasInternetAndValidated =
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        if (!hasInternetAndValidated) return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    val isConnected: Flow<Boolean>
        get() = callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(true)
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities,
                ) {
                    super.onCapabilitiesChanged(network, networkCapabilities)
                    trySend(isNetworkEffectivelyConnected(networkCapabilities))
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(false)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(false)
                }
            }
            val activeNetwork = connectivityManager.activeNetwork
            val capabilities = activeNetwork?.let { connectivityManager.getNetworkCapabilities(it) }
            trySend(isNetworkEffectivelyConnected(capabilities))
            connectivityManager.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
}