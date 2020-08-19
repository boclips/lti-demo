package com.boclips.lti.demo.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.boclips.lti.demo.domain.LtiCustomClaimKey
import com.boclips.lti.demo.domain.LtiMessageType
import java.net.URL

class AssembleIdToken(
    private val getSigningKeys: GetSigningKeys
) {
    operator fun invoke(
        clientId: String,
        issuer: URL,
        targetLinkUri: URL,
        subject: String
    ): String =
        JWT.create()
            .withDefaultLti1p3Claims(issuer = issuer, clientId = clientId, targetLinkUri = targetLinkUri, subject = subject)
            .withClaim(LtiCustomClaimKey.MESSAGE_TYPE.value, LtiMessageType.RESOURCE_LINK_REQUEST.value)
            .sign(Algorithm.RSA256(getSigningKeys.keyPair.public, getSigningKeys.keyPair.private))
}
