package com.boclips.lti.demo.application

import com.auth0.jwt.JWT
import com.boclips.lti.demo.domain.DeepLinkingResult
import com.boclips.lti.demo.domain.DeepLinkingSelectedContent
import org.springframework.stereotype.Component

@Component
class DecodeDeepLinkResponse {
    operator fun invoke(jwt: String): DeepLinkingResult {
        // TODO unhappy path
        // TODO some validation of signatures?
        val decodedToken = JWT.decode(jwt)
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
