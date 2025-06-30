package com.example.proyectofinal_recetas.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class ObservadorConectividad @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _conectado = MutableStateFlow(false)
    val conectado: StateFlow<Boolean> = _conectado.asStateFlow()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _conectado.value = true
        }

        override fun onLost(network: Network) {
            _conectado.value = false
        }
    }

    init {
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, callback)
    }

    override fun onCleared() {
        super.onCleared()
        connectivityManager.unregisterNetworkCallback(callback)
    }
}