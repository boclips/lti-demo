package com.boclips.lti.demo.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/auth")
class AuthenticationController {

    @GetMapping
    fun authenticate(
        @NotNull
        @RequestParam redirect_uri: String,
        @NotNull
        @RequestParam login_hint: String,
        @NotNull
        @RequestParam scope: String,
        @NotNull
        @RequestParam response_type: String,
        @NotNull
        @RequestParam client_id: String,
        @NotNull
        @RequestParam state: String,
        @NotNull
        @RequestParam response_mode: String,
        @NotNull
        @RequestParam nonce: String,
        @NotNull
        @RequestParam prompt: String,
        @RequestParam lti_message_hint: String?
    ) = ModelAndView(
        "auth-success",
        mapOf("state" to state, "idToken" to "garbage", "redirectUri" to redirect_uri)
    )
}