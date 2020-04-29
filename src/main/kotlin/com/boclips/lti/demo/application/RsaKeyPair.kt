package com.boclips.lti.demo.application

import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

data class RsaKeyPair(val private: RSAPrivateKey, val public: RSAPublicKey) {
    companion object {
        fun fromKeyStrings(privateStr: String, publicStr: String): RsaKeyPair {
            val privateKeyContent = privateKeyContent(privateStr)
            val publicKeyContent = publicKeyContent(publicStr)

            val kf: KeyFactory = KeyFactory.getInstance("RSA")

            val privateKeySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent))
            val privateKey: PrivateKey = kf.generatePrivate(privateKeySpec)

            val publicKeySpec = X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent))
            val publicKey = kf.generatePublic(publicKeySpec)

            return RsaKeyPair(
                private = privateKey as RSAPrivateKey,
                public = publicKey as RSAPublicKey
            )
        }

        private fun publicKeyContent(publicKey: String) =
            publicKey.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replace("\\n", "")
                .replace(" ", "")

        private fun privateKeyContent(privateKey: String) =
            privateKey.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\\n", "")
                .replace(" ", "")
    }
}