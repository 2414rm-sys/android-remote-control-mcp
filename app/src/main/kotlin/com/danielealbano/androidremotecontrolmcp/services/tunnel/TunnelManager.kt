package com.danielealbano.androidremotecontrolmcp.services.tunnel

import com.danielealbano.androidremotecontrolmcp.data.model.TunnelStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * TEND-STRIP: tunnel support removed.
 *
 * The Cloudflare/ngrok providers (which downloaded and executed external
 * binaries and exposed the device through public URLs) have been deleted.
 * This build is Tailscale-only: the MCP server binds to the device's network
 * interfaces and is reached via the tailnet. No public tunnel can exist.
 *
 * This no-op keeps the [McpServerService] and UI call sites compiling
 * without modification.
 */
@Singleton
class TunnelManager @Inject constructor() {
    private val _tunnelStatus = MutableStateFlow<TunnelStatus>(TunnelStatus.Disconnected)
    val tunnelStatus: StateFlow<TunnelStatus> = _tunnelStatus.asStateFlow()

    suspend fun start(localPort: Int) {
        // No-op: tunnels are removed in this build.
    }

    suspend fun stop() {
        // No-op.
    }
}
