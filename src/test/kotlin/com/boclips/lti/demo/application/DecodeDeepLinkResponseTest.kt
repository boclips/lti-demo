package com.boclips.lti.demo.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.boclips.lti.demo.domain.DeepLinkingSelectedContent
import com.boclips.lti.demo.domain.LtiMessageType
import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import com.github.tomakehurst.wiremock.WireMockServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import ru.lanwen.wiremock.ext.WiremockResolver
import ru.lanwen.wiremock.ext.WiremockUriResolver
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Instant
import java.util.*

@ExtendWith(
        value = [
            WiremockResolver::class,
            WiremockUriResolver::class
        ]
)
class DecodeDeepLinkResponseTest : AbstractSpringIntegrationTest() {
    @Test
    fun `decodes a valid deep linking jwt`(
            @WiremockResolver.Wiremock server: WireMockServer,
            @WiremockUriResolver.WiremockUri uri: String
    ) {
        val tokenSigningSetup = setupTokenSigning(server, uri)
        val decodeDeepLinkResponse = DecodeDeepLinkResponse(tokenSigningSetup.jwksUrl)
        val token = sampleResourceLinkRequestJwt(
                issuer = "https://example.com",
                signatureAlgorithm = Algorithm.RSA256(
                        tokenSigningSetup.keyPair.first,
                        tokenSigningSetup.keyPair.second
                ),
                selectedContent = SelectedContent(
                        url = "thisisaurl",
                        title = "this is a title",
                        text = "this is a description"
                ),
                data = "thisisdata"
        )

        val deepLinkingResult = decodeDeepLinkResponse(token)

        assertThat(deepLinkingResult.data).isEqualTo("thisisdata")
        assertThat(deepLinkingResult.selectedContents).containsExactlyInAnyOrder(
                DeepLinkingSelectedContent(
                        type = "ltiResourceLink",
                        url = "thisisaurl",
                        title = "this is a title",
                        text = "this is a description"
                )
        )
    }

    @Test
    fun `throws an error if signature verification fails`(
            @WiremockResolver.Wiremock server: WireMockServer,
            @WiremockUriResolver.WiremockUri uri: String
    ) {
        val tokenSigningSetup = setupTokenSigning(server, uri)
        val decodeDeepLinkResponse = DecodeDeepLinkResponse(tokenSigningSetup.jwksUrl)

        val issuer = "https://example.com/"

        val otherKeyPair = KeyPairGenerator.getInstance("RSA").genKeyPair()

        val token = sampleResourceLinkRequestJwt(
                issuer = issuer,
                signatureAlgorithm = Algorithm.RSA256(
                        otherKeyPair.public as RSAPublicKey,
                        otherKeyPair.private as RSAPrivateKey
                )
        )

        assertThrows<InvalidSignatureException> {
            decodeDeepLinkResponse(token)
        }
    }

    fun sampleResourceLinkRequestJwt(
            issuer: String = "https://lms.com",
            audience: List<String> = listOf("some audience"),
            selectedContent: SelectedContent = SelectedContent(
                    url = "https://lti.staging-boclips.com/embeddable-videos/5c92b2f5d0f34e48bbfb40da",
                    title = "this is a title",
                    text = "this is the text"
            ),
            data: String = "iamdata",
            signatureAlgorithm: Algorithm
    ): String {
        return JWT.create()
                .withIssuer(issuer)
                .withAudience(*audience.toTypedArray())
                .withExpiresAt(Date.from(Instant.now().plusSeconds(10)))
                .withIssuedAt(Date.from(Instant.now().minusSeconds(10)))
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("https://purl.imsglobal.org/spec/lti/claim/deployment_id", "test-deployment-id")
                .withClaim(
                        "https://purl.imsglobal.org/spec/lti/claim/message_type",
                        LtiMessageType.DEEP_LINKING_REQUEST.value
                )
                .withClaim("https://purl.imsglobal.org/spec/lti/claim/version", "1.3.0")
                .withClaim(
                        "https://purl.imsglobal.org/spec/lti-dl/claim/content_items", listOf(
                        mapOf(
                                "type" to "ltiResourceLink",
                                "url" to selectedContent.url,
                                "title" to selectedContent.title,
                                "text" to selectedContent.text
                        )
                )
                )
                .withClaim(
                        "https://purl.imsglobal.org/spec/lti/claim/resource_link",
                        mapOf("id" to "test-resource-link-id")
                )
                .withClaim("https://purl.imsglobal.org/spec/lti-dl/claim/data", data)
                .sign(signatureAlgorithm)
    }

    inner class SelectedContent(
            val url: String = "thisisaurl",
            val title: String = "this is a title",
            val text: String = "this is a description"
    )
}
