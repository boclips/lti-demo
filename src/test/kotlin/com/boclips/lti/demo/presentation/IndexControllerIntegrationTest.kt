package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class IndexControllerIntegrationTest : AbstractSpringIntegrationTest() {
    @Test
    fun `renders a landing page`() {
        mvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(
                content().string(
                    containsString(
                        """
                            <a href="https://google.com">Google</a>
                        """.trimIndent()
                    )
                )
            )
    }
}