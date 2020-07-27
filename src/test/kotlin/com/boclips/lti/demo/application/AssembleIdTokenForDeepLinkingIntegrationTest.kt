package com.boclips.lti.demo.application

import com.auth0.jwt.JWT
import com.boclips.lti.demo.domain.LtiCustomClaimKey
import com.boclips.lti.demo.domain.LtiMessageType
import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.net.URL

class AssembleIdTokenForDeepLinkingIntegrationTest : AbstractSpringIntegrationTest() {
    @Autowired
    lateinit var assembleIdTokenForDeepLinking: AssembleIdTokenForDeepLinking

    @Test
    fun `sets deep linking claims`() {
        val clientId = "my-client"
        val issuer = URL("http://cool.spot")
        val targetLinkUri = URL("http://my-cool-uri.bike")
        val encoded = assembleIdTokenForDeepLinking(
            clientId,
            issuer,
            targetLinkUri,
            deepLinkReturnUrl = "http://my-cool-uri.deep-link"
        )
        val decoded = JWT.decode(encoded)

        assertThat(
            decoded.getClaim(LtiCustomClaimKey.MESSAGE_TYPE.value).asString()
        ).isEqualTo(LtiMessageType.DEEP_LINKING_REQUEST.value)

        assertThat(decoded.getClaim(LtiMessageType.DEEP_LINKING_REQUEST.value)).isNotNull

        val deepLinkingSettings = decoded.getClaim(LtiCustomClaimKey.DEEP_LINKING_SETTINGS.value).asMap()
        assertThat(deepLinkingSettings["deep_link_return_url"]).isEqualTo("http://my-cool-uri.deep-link")
        assertThat(deepLinkingSettings["accept_types"]).isEqualTo(listOf("ltiResourceLink"))
        assertThat(deepLinkingSettings["accept_presentation_document_targets"]).isEqualTo(listOf("iframe"))
    }
}
