package com.boclips.lti.demo.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.boclips.lti.demo.domain.LtiCustomClaimKey
import com.boclips.lti.demo.domain.LtiMessageType
import com.boclips.lti.demo.domain.LtiVersion
import java.net.URL
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.UUID

class AssembleIdToken(
    private val getSigningKeys: GetSigningKeys
) {
    operator fun invoke(clientId: String, issuer: URL, targetLinkUri: URL): String =
        JWT.create()
            .withIssuer(issuer.toString())
            .withAudience(clientId)
            .withClaim("azp", clientId)
            .withIssuedAt(Date.from(Instant.now()))
            .withExpiresAt(Date.from(Instant.now().plus(5, ChronoUnit.MINUTES)))
            .withClaim("nonce", UUID.randomUUID().toString())
            .withClaim(LtiCustomClaimKey.DEPLOYMENT_ID.value, UUID.randomUUID().toString())
            .withClaim(LtiCustomClaimKey.TARGET_LINK_URI.value, targetLinkUri.toString())
            .withClaim(LtiCustomClaimKey.MESSAGE_TYPE.value, LtiMessageType.RESOURCE_LINK_REQUEST.value)
            .withClaim(LtiCustomClaimKey.VERSION.value, LtiVersion.ONE_POINT_THREE.value)
            .withClaim(
                LtiCustomClaimKey.RESOURCE_LINK.value,
                mapOf("id" to UUID.randomUUID().toString())
            )
            .sign(Algorithm.RSA256(getSigningKeys.keyPair.public, getSigningKeys.keyPair.private))
}