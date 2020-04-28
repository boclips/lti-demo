package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.application.GetSigningKeys
import org.jose4j.jwk.JsonWebKeySet
import org.jose4j.jwk.PublicJsonWebKey
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/jwks")
class JwksController(
    private val getKeys: GetSigningKeys
) {
    @GetMapping
    fun keySet(): String =
        JsonWebKeySet(
            PublicJsonWebKey.Factory.newPublicJwk(getKeys.keyPair.public)
        ).toJson()
}