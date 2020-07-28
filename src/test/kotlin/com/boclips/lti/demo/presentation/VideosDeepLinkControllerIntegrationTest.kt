package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.application.DecodeDeepLinkResponse
import com.boclips.lti.demo.domain.DeepLinkingResult
import com.boclips.lti.demo.domain.DeepLinkingSelectedContent
import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.Matchers.contains
import org.junit.jupiter.api.Test
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class VideosDeepLinkControllerIntegrationTest : AbstractSpringIntegrationTest() {
    @MockBean
    lateinit var decodeDeepLinkResponse: DecodeDeepLinkResponse

    @Test
    fun `returns page with deep link selection rendered into form`() {
        val token = "jwt token"

        whenever(decodeDeepLinkResponse(token)).thenReturn(
            DeepLinkingResult(
                data = "blah", selectedContents = listOf(
                    DeepLinkingSelectedContent(
                        type = "any",
                        url = "https://great-content-over-here.com"
                    )
                )
            )
        )

        mvc.perform(
            post("/videos-deep-link")
                .param("JWT", token)
        )
            .andExpect(view().name("videos-deep-link"))
            .andExpect(
                model().attribute(
                    "selectedVideos",
                    contains("https://great-content-over-here.com")
                )
            )
    }
}
