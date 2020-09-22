package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.application.DecodeDeepLinkResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/videos-deep-link")
class VideosDeepLinkController(
    private val decodeDeepLinkResponse: DecodeDeepLinkResponse
) {
    @PostMapping
    fun deepLink(@RequestParam("JWT") jwt: String): ModelAndView {
        val decodedResult = decodeDeepLinkResponse(jwt)

        return ModelAndView(
            "videos-deep-link",
            mapOf("selectedVideos" to decodedResult.selectedContents)
        )
    }
}

