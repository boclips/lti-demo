package com.boclips.lti.demo.presentation

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController(@Value("\${boclips.lti.initiateLoginUrl}") private val initiateLoginUrl: String) {
    @GetMapping("/")
    fun landingPage() = ModelAndView(
        "index", mapOf(
            "initiateLoginUrl" to initiateLoginUrl
        )
    )
}
