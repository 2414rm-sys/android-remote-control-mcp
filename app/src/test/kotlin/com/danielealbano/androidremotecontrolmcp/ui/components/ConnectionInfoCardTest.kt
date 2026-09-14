package com.danielealbano.androidremotecontrolmcp.ui.components

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

/**
 * Unit tests verifying the ConnectionInfoCard connection-string logic (the pure
 * [buildConnectionString] function, exercised without a Compose runtime).
 */
class ConnectionInfoCardTest {
    @Test
    fun `buildConnectionString includes url with mcp suffix`() {
        val connectionString =
            buildConnectionString(
                serverUrl = "http://127.0.0.1:8080/mcp",
                bearerToken = "test-token-123",
            )
        assertEquals(
            "URL: http://127.0.0.1:8080/mcp\nBearer Token: test-token-123",
            connectionString,
        )
    }

    @Test
    fun `buildConnectionString works with https url`() {
        val connectionString =
            buildConnectionString(
                serverUrl = "https://192.168.1.100:8443/mcp",
                bearerToken = "test-token-123",
            )
        assertEquals(
            "URL: https://192.168.1.100:8443/mcp\nBearer Token: test-token-123",
            connectionString,
        )
    }

    @Test
    fun `buildConnectionString always uses the real bearer token`() {
        val realToken = "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
        val connectionString =
            buildConnectionString(
                serverUrl = "http://127.0.0.1:8080/mcp",
                bearerToken = realToken,
            )
        assertTrue(
            connectionString.contains("Bearer Token: $realToken"),
            "Connection string should contain the real bearer token",
        )
        assertFalse(
            connectionString.contains("********"),
            "Connection string should never contain masked token",
        )
    }

    @Test
    fun `buildConnectionString omits bearer token when empty`() {
        val connectionString =
            buildConnectionString(
                serverUrl = "http://127.0.0.1:8080/mcp",
                bearerToken = "",
            )
        assertEquals("URL: http://127.0.0.1:8080/mcp", connectionString)
        assertFalse(connectionString.contains("Bearer Token"))
    }
}
