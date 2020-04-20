package com.boclips.lti.demo.presentation

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class IndexController {
    @GetMapping("/")
    fun landingPage() = "index"
}
