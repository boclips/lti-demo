package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.application.AssembleIdToken
import com.boclips.lti.demo.application.AssembleIdTokenForDeepLinking
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.net.URL
import javax.validation.constraints.NotNull

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    val assembleIdToken: AssembleIdToken,
    val assembleIdTokenForDeepLinking: AssembleIdTokenForDeepLinking,
    @Value("\${boclips.lti.issuerUrl}") private val issuerUrl: String
) {

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
        @NotNull
        @RequestParam lti_message_hint: String
    ): ModelAndView {
        val token = if (lti_message_hint.contains("search-and-embed")) {
            assembleIdTokenForDeepLinking(
                clientId = client_id,
                issuer = URL(issuerUrl),
                targetLinkUri = URL(lti_message_hint),
                deepLinkReturnUrl = "$issuerUrl/videos-deep-link"
            )
        } else {
            assembleIdToken(
                clientId = client_id,
                issuer = URL(issuerUrl),
                targetLinkUri = URL(lti_message_hint)
            )
        }

        return ModelAndView(
            "auth-success",
            mapOf("state" to state, "idToken" to token, "redirectUri" to redirect_uri)
        )
    }
}
