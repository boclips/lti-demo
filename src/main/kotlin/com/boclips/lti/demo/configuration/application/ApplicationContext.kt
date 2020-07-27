package com.boclips.lti.demo.configuration.application

import com.boclips.lti.demo.application.AssembleIdToken
import com.boclips.lti.demo.application.AssembleIdTokenForDeepLinking
import com.boclips.lti.demo.application.GetSigningKeys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ApplicationContext(
    @Value("\${boclips.lti.privateSigningKey}") val privateKey: String,
    @Value("\${boclips.lti.publicSigningKey}") val publicKey: String
) {
    @Bean
    fun getSigningKeys() = GetSigningKeys(privateKey = privateKey, publicKey = publicKey)

    @Bean
    fun assembleIdToken(getSigningKeys: GetSigningKeys) =
        AssembleIdToken(getSigningKeys())

    @Bean
    fun assembleIdTokenForDeepLinking(getSigningKeys: GetSigningKeys) =
        AssembleIdTokenForDeepLinking(getSigningKeys())
}
