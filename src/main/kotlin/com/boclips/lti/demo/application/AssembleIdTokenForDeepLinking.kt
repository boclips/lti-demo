package com.boclips.lti.demo.application

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import com.boclips.lti.demo.domain.LtiCustomClaimKey
import com.boclips.lti.demo.domain.LtiMessageType
import com.boclips.lti.demo.domain.LtiVersion
import java.net.URL
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.UUID

class AssembleIdTokenForDeepLinking(
    private val getSigningKeys: GetSigningKeys
) {
    operator fun invoke(
        clientId: String,
        issuer: URL,
        targetLinkUri: URL,
        deepLinkReturnUrl: String,
        subject: String
    ): String =
        JWT.create()
            .withDefaultLti1p3Claims(issuer, clientId, targetLinkUri, subject)
            .withDeepLinkingClaims(deepLinkReturnUrl)
            .sign(Algorithm.RSA256(getSigningKeys.keyPair.public, getSigningKeys.keyPair.private))
}
