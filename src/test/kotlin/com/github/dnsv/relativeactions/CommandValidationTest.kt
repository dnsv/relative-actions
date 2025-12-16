package com.github.dnsv.relativeactions

import com.github.dnsv.relativeactions.util.BaseTestCase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CommandValidationTest : BaseTestCase() {
    @Test
    fun `test no binding for key`() {
        makeEditor("")

        performCommand("g")

        Assertions.assertEquals("No binding for key: 'g'.", getLastNotification().content)
    }

    @Test
    fun `test cannot use bidirectional key for movement`() {
        makeEditor("")

        performCommand("3b")

        Assertions.assertEquals(
            "Invalid command '3b': 'b' (bidirectional) cannot be used for movement.",
            getLastNotification().content,
        )
    }

    @Test
    fun `test cannot use beginning of line key with an action`() {
        makeEditor("")

        performCommand("wck")

        Assertions.assertEquals(
            "Invalid command 'wck': 'w' (beginning of line) can only be used for movement.",
            getLastNotification().content,
        )
    }

    @Test
    fun `test cannot use end of line key with an action`() {
        makeEditor("")

        performCommand("edk")

        Assertions.assertEquals(
            "Invalid command 'edk': 'e' (end of line) can only be used for movement.",
            getLastNotification().content,
        )
    }
}
