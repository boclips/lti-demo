package com.boclips.lti.demo.testsupport

import com.boclips.lti.demo.application.GetSigningKeys
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.util.Base64
import java.util.UUID

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
abstract class AbstractSpringIntegrationTest {
    @Autowired
    protected lateinit var mvc: MockMvc

    @Autowired
    protected lateinit var getSigningKeys: GetSigningKeys

    protected fun stubJwksResponse(
        server: WireMockServer,
        publicKeyId: String,
        encodedModulus: String,
        encodedExponent: String
    ) {
        server
            .stubFor(
                WireMock.get(WireMock.urlEqualTo("/jwks"))
                    .willReturn(
                        WireMock.aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(
                                """
                                {
                                  "keys": [
                                    {
                                      "kid": "$publicKeyId",
                                      "kty": "RSA",
                                      "alg": "RS256",
                                      "use": "sig",
                                      "n": "$encodedModulus",
                                      "e": "$encodedExponent",
                                      "x5c": [
                                        "MIICnTCCAYUCBgFxG80tMzANBgkqhkiG9w0BAQsFADASMRAwDgYDVQQDDAdib2NsaXBzMB4XDTIwMDMyNzExNDEwM1oXDTMwMDMyNzExNDI0M1owEjEQMA4GA1UEAwwHYm9jbGlwczCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKAWvMyHyCQJnJ5rIqxMCDYtag+cOWE+mhDjVvW8J0LUCTHuZ/kiBKPIaW0AH427CgtTZ2VXCSy6jlZxM0BmNLtJtwG1bEAOXEIO3w6DfEGEAb03LzDMIPbvuNeNk1Pb2G8y3X73umlRWcEsz697XT5RgFx7GWKz4fOpTDh+e9kDxCjEhc3or+P6u3NbSX3kP4Ywl8EQHBgUo2mwWVfY7PeGTzo2+1DdCHpFcrkcLhgEt+TTs7yqPP3k1bBqiQrNXvz9eLS1/qzEW8GMqdywBLDHmZ/PDLJla5HzBAUohUnw/QEAWVyFDiF/1a5eIlT4v/y5nAiCBs+P9uWXtmf4UlUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAL3sB4eM8/lnwEQ3IK53uLVRkbGThNZEesAEowIX+XSp8r4x5gFlqE72kKa5SFX1B9w9N1f5msOYZoO5MraDxyeCHNlGvl5udniub5mrFJfQF3/+vGqi/03ckE+Salj1P3piWGMw+YLZ/igXMneRxFzErH1izg0lqaB9sgdX7NCjtkAUesSbznV5/9/jCtNs7UvoSR6f16p4lvH9IKZcJzRaYZz6MXkVzkt2ViKcl9vExOAUzwL306RVtFTginkElLwvxBNKmo9MspTSKdoZICB+8CJRFxzlqYWgtmCscWzCQG0SHcVXzUJu5gnB0ARkzpe6e4bHoSKrtTXCeKuBVFA=="
                                      ],
                                      "x5t": "9nrzejjjfL8wbj77uco0sf5YLwY",
                                      "x5t#S256": "fmOamYW3tK_fgc-D5BltvRqaaZC0FdhzlxPwXijZqHA"
                                    }
                                  ]
                                }
                            """.trimIndent()
                            )
                    )
            )
    }

    protected fun setupTokenSigning(server: WireMockServer, uri: String): TokenSigningSetup {
        val publicKeyId = UUID.randomUUID().toString()
        val keyPair = KeyPairGenerator.getInstance("RSA").genKeyPair()
        val rsaPublicKey = keyPair.public as RSAPublicKey

        stubJwksResponse(
            server,
            publicKeyId,
            Base64.getUrlEncoder().encodeToString(rsaPublicKey.modulus.toByteArray()),
            Base64.getUrlEncoder().encodeToString(rsaPublicKey.publicExponent.toByteArray())
        )

        return TokenSigningSetup(
            keyPair = rsaPublicKey to keyPair.private as RSAPrivateKey,
            jwksUrl = "$uri/jwks"
        )
    }

    data class TokenSigningSetup(
        val keyPair: Pair<RSAPublicKey, RSAPrivateKey>,
        val jwksUrl: String
    )
}
