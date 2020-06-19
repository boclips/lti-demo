package com.boclips.lti.demo.presentation

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexPageController(
    @Value("\${boclips.lti.initiateLoginUrl}") private val initiateLoginUrl: String,
    @Value("\${boclips.authentication.endPoint}") private val authEndpoint: String,
    @Value("\${boclips.lti.issuerUrl}") private val issuerUrl: String,
    @Value("\${boclips.lti.baseUrl}") private val ltiBaseUrl: String
) {
    @GetMapping("/")
    fun landingPage() = ModelAndView(
        "index", mapOf(
            "initiateLoginUrl" to initiateLoginUrl,
            "authEndpoint" to authEndpoint,
            "issuerUrl" to issuerUrl,
            "ltiBaseUrl" to ltiBaseUrl
        )
    )
}
