package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import org.hamcrest.CoreMatchers.containsString
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class IndexPageControllerIntegrationTest : AbstractSpringIntegrationTest() {
    @Test
    fun `renders a landing with expected model parameters`() {
        mvc.perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(view().name("index"))
            .andExpect(model().attribute("initiateLoginUrl", "http://localhost:8080/v1p3/initiate-login"))
            .andExpect(model().attribute("authEndpoint", "https://login.staging-boclips.com/auth"))
            .andExpect(content().string(containsString("http://localhost:8080/v1p3/initiate-login")))
            .andExpect(content().string(containsString("https:\\/\\/login.staging-boclips.com\\/auth")))
    }
}
