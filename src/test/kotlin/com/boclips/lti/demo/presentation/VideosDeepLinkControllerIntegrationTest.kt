package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import org.hamcrest.Matchers.contains
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class VideosDeepLinkControllerIntegrationTest : AbstractSpringIntegrationTest() {
    @Test
    fun `returns page with deep link selection rendered into form`() {
        // payload is
        // {
        //     "aud": "https://lti-demo.staging-boclips.com",
        //     "https://purl.imsglobal.org/spec/lti/claim/message_type": "LtiDeepLinkingResponse",
        //     "https://purl.imsglobal.org/spec/lti/claim/deployment_id": "344d0ed5-81f8-4d4b-a3e3-4c0804e0cf53",
        //     "https://purl.imsglobal.org/spec/lti-dl/claim/content_items": [
        //     {
        //         "type": "ltiResourceLink",
        //         "url": "https://lti.staging-boclips.com/embeddable-videos/5c92b2f5d0f34e48bbfb40da"
        //     }
        //     ],
        //     "https://purl.imsglobal.org/spec/lti/claim/version": "1.3.0",
        //     "iss": "boclips",
        //     "exp": 1595602764,
        //     "iat": 1595602704,
        //     "nonce": "c39662e5-e7df-4021-9902-5f19d428b01c",
        //     "https://purl.imsglobal.org/spec/lti-dl/claim/data": "null"
        // }

        val token =
            "eyJraWQiOiIxNTk0NzI0MzE5IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJhdWQiOiJodHRwczovL2x0aS1kZW1vLnN0YWdpbmctYm9jbGlwcy5jb20iLCJodHRwczovL3B1cmwuaW1zZ2xvYmFsLm9yZy9zcGVjL2x0aS9jbGFpbS9tZXNzYWdlX3R5cGUiOiJMdGlEZWVwTGlua2luZ1Jlc3BvbnNlIiwiaHR0cHM6Ly9wdXJsLmltc2dsb2JhbC5vcmcvc3BlYy9sdGkvY2xhaW0vZGVwbG95bWVudF9pZCI6IjM0NGQwZWQ1LTgxZjgtNGQ0Yi1hM2UzLTRjMDgwNGUwY2Y1MyIsImh0dHBzOi8vcHVybC5pbXNnbG9iYWwub3JnL3NwZWMvbHRpLWRsL2NsYWltL2NvbnRlbnRfaXRlbXMiOlt7InR5cGUiOiJsdGlSZXNvdXJjZUxpbmsiLCJ1cmwiOiJodHRwczovL2x0aS5zdGFnaW5nLWJvY2xpcHMuY29tL2VtYmVkZGFibGUtdmlkZW9zLzVjOTJiMmY1ZDBmMzRlNDhiYmZiNDBkYSJ9XSwiaHR0cHM6Ly9wdXJsLmltc2dsb2JhbC5vcmcvc3BlYy9sdGkvY2xhaW0vdmVyc2lvbiI6IjEuMy4wIiwiaXNzIjoiYm9jbGlwcyIsImV4cCI6MTU5NTYwMjc2NCwiaWF0IjoxNTk1NjAyNzA0LCJub25jZSI6ImMzOTY2MmU1LWU3ZGYtNDAyMS05OTAyLTVmMTlkNDI4YjAxYyIsImh0dHBzOi8vcHVybC5pbXNnbG9iYWwub3JnL3NwZWMvbHRpLWRsL2NsYWltL2RhdGEiOiJudWxsIn0.EfoM-H9e5gbVRIZtBGZ1GIHTbCBvs4OrQWPYKmI_ldvO6XnHjK1mjube5l-NTpA7yAZ3R4i-0jSmMH991Lhli_D_eXuW2VFqx3AfxGecxtKa6GIo4-4hcv9dJAPhZMIcG87g-I9K_CjlkRDuYFaIoZhQoafXDQGyDsYCjpRXIZ16wTENvHVYO8dtjrFkNJMzeIWbWnR4Ju_IJCMr08hUhBZ4OIPDZZGvLbTGxgJqZqNtUtBNelpOiDsn6kmadexVKkDSjYtDDdclti7HpDDdXFofKBlvYQ0uvZRx4HpVuxtftdaRdbMrqb4umSSNa0n1YIIV8pL41mq1CCCiCxjaTA"

        mvc.perform(
            post("/videos-deep-link")
                .param("JWT", token)
        )
            .andExpect(view().name("videos-deep-link"))
            .andExpect(
                model().attribute(
                    "selectedVideos",
                    contains("https://lti.staging-boclips.com/embeddable-videos/5c92b2f5d0f34e48bbfb40da")
                )
            )
    }
}
