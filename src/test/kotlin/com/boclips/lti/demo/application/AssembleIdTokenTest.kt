package com.boclips.lti.demo.application

import com.auth0.jwt.JWT
import com.boclips.lti.demo.domain.LtiCustomClaimKey
import com.boclips.lti.demo.domain.LtiMessageType
import com.boclips.lti.demo.domain.LtiVersion
import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.net.URL

class AssembleIdTokenTest : AbstractSpringIntegrationTest() {
    @Autowired
    lateinit var assembleIdToken: AssembleIdToken

    @Test
    fun `encode and decode token, and verify correct claims are present`() {
        val clientId = "my-client"
        val issuer = URL("http://cool.spot")
        val targetLinkUri = URL("http://my-cool-uri.bike")
        val encoded = assembleIdToken(clientId, issuer, targetLinkUri)
        val decoded = JWT.decode(encoded)
        assertThat(decoded.issuer).isEqualTo(issuer.toString())
        assertThat(decoded.audience).containsExactly(clientId)
        assertThat(decoded.getClaim("azp").asString()).isEqualTo(clientId)
        assertThat(decoded.issuedAt).isNotNull()
        assertThat(decoded.expiresAt).isNotNull()
        assertThat(decoded.getClaim("nonce").asString()).isNotBlank()
        assertThat(
            decoded.getClaim(LtiCustomClaimKey.DEPLOYMENT_ID.value).asString()
        ).isNotBlank()
        assertThat(
            decoded.getClaim(LtiCustomClaimKey.TARGET_LINK_URI.value).asString()
        ).isEqualTo(targetLinkUri.toString())
        assertThat(
            decoded.getClaim(LtiCustomClaimKey.MESSAGE_TYPE.value).asString()
        ).isEqualTo(LtiMessageType.RESOURCE_LINK_REQUEST.value)
        assertThat(
            decoded.getClaim(LtiCustomClaimKey.VERSION.value).asString()
        ).isEqualTo(LtiVersion.ONE_POINT_THREE.value)
        assertThat(
            decoded.getClaim(LtiCustomClaimKey.RESOURCE_LINK.value).asMap()["id"] as String
        ).isNotBlank()
    }
}
