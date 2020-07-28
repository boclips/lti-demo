package com.boclips.lti.demo.application

import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.interfaces.RSAKeyProvider
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

open class JwksKeyProvider(private val keyProvider: UrlJwkProvider) : RSAKeyProvider {
    override fun getPrivateKeyId(): String {
        TODO("Not yet implemented")
    }

    override fun getPrivateKey(): RSAPrivateKey {
        TODO("Not yet implemented")
    }

    override fun getPublicKeyById(keyId: String?): RSAPublicKey {
        return keyProvider.get(keyId).publicKey as RSAPublicKey
    }
}
