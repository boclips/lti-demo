package com.boclips.lti.demo.application

class GetSigningKeys(
    privateKey: String, publicKey: String
) {
    var keyPair: RsaKeyPair = RsaKeyPair.fromKeyStrings(
        privateStr = privateKey,
        publicStr = publicKey
    )
}