package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.emptyString
import org.hamcrest.Matchers.nullValue
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class AuthenticationControllerIntegrationTest : AbstractSpringIntegrationTest() {
    @Test
    fun `returns a page with state and id_token rendered into a form`() {
        val state = "my state"

        mvc.perform(
            get("/auth")
                .queryParam("scope", "openid")
                .queryParam("response_type", "id_token")
                .queryParam("client_id", "boclips")
                .queryParam("login_hint", "login hint")
                .queryParam("state", state)
                .queryParam("response_mode", "form_post")
                .queryParam("nonce", "some-uuid")
                .queryParam("prompt", "none")
                .queryParam("redirect_uri", "https://tool.com/auth-response")
        )
            .andExpect(status().isOk)
            .andExpect(view().name("auth-success"))
            .andExpect(model().attribute("idToken", not(nullValue())))
            .andExpect(model().attribute("idToken", not(emptyString())))
            .andExpect(content().string(containsString(state)))
    }

    @Test
    fun `returns a bad request when redirect_uri is missing`() {
        mvc.perform(
            get("/auth")
                .queryParam("scope", "openid")
                .queryParam("response_type", "id_token")
                .queryParam("client_id", "boclips")
                .queryParam("login_hint", "login hint")
                .queryParam("state", "my state")
                .queryParam("response_mode", "form_post")
                .queryParam("nonce", "some-uuid")
                .queryParam("prompt", "none")
        )
            .andExpect(status().isBadRequest)
    }
}