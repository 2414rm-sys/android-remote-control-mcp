package com.danielealbano.androidremotecontrolmcp.integration

import com.danielealbano.androidremotecontrolmcp.data.model.OptionalToolPermission
import com.danielealbano.androidremotecontrolmcp.data.model.OptionalToolPermissions
import com.danielealbano.androidremotecontrolmcp.data.model.ToolPermissionsConfig
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Permission-gated Tools Integration Tests")
class PermissionGatedToolsIntegrationTest {
    @BeforeEach
    fun setUp() {
        McpIntegrationTestHelper.mockAndroidLog()
    }

    @AfterEach
    fun tearDown() {
        McpIntegrationTestHelper.unmockAndroidLog()
    }

    /** Effective config with only the given optional permissions granted (nothing disabled by the user). */
    private fun permsFor(granted: Set<OptionalToolPermission>): ToolPermissionsConfig =
        OptionalToolPermissions.effectivePermissions(ToolPermissionsConfig(), granted)

    @Test
    fun `notification tools absent when listener not granted`() =
        runTest {
            val granted = ALL - OptionalToolPermission.NOTIFICATION_LISTENER

            McpIntegrationTestHelper.withTestApplication(perms = permsFor(granted)) { client, _ ->
                val toolNames =
                    client
                        .listTools()
                        .tools
                        .map { it.name }
                        .toSet()
                PREFIXED_NOTIFICATION_TOOLS.forEach { name ->
                    assertFalse(toolNames.contains(name), "$name should be hidden when listener not granted")
                }
            }
        }

    @Test
    fun `notification tools present when listener granted`() =
        runTest {
            McpIntegrationTestHelper.withTestApplication(perms = permsFor(ALL)) { client, _ ->
                val toolNames =
                    client
                        .listTools()
                        .tools
                        .map { it.name }
                        .toSet()
                PREFIXED_NOTIFICATION_TOOLS.forEach { name ->
                    assertTrue(toolNames.contains(name), "$name should be present when listener granted")
                }
            }
        }

    companion object {
        private val ALL: Set<OptionalToolPermission> = OptionalToolPermission.entries.toSet()

        private val PREFIXED_NOTIFICATION_TOOLS =
            setOf(
                "android_notification_list",
                "android_notification_open",
                "android_notification_dismiss",
                "android_notification_snooze",
                "android_notification_action",
                "android_notification_reply",
            )
    }
}
