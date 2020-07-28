package com.boclips.lti.demo.application

import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.boclips.lti.demo.domain.DeepLinkingResult
import com.boclips.lti.demo.domain.DeepLinkingSelectedContent
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URL

@Component
class DecodeDeepLinkResponse(
    @Value("\${boclips.lti.jwksUrl}") val jwksUrl: String
) {
    operator fun invoke(jwt: String): DeepLinkingResult {
        val decodedToken = JWT.decode(jwt)
        val keyProvider = UrlJwkProvider(URL(jwksUrl), 1000, 1000)

        val algorithm = Algorithm.RSA256(JwksKeyProvider(keyProvider))

        try {
            algorithm.verify(decodedToken)
        } catch (e: SignatureVerificationException) {
            throw InvalidSignatureException()
        }

        val items =
            decodedToken.claims["https://purl.imsglobal.org/spec/lti-dl/claim/content_items"]
                ?.asList(MutableMap::class.java)
                ?.map { item ->
                    DeepLinkingSelectedContent(
                        type = item["type"] as String,
                        url = item["url"] as String
                    )
                }
        val data = decodedToken.claims["https://purl.imsglobal.org/spec/lti-dl/claim/data"]?.asString()

        return DeepLinkingResult(
            selectedContents = items!!,
            data = data!!
        )
    }
}
