package com.boclips.lti.demo.presentation

import com.boclips.lti.demo.testsupport.AbstractSpringIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.jose4j.jwk.JsonWebKeySet
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class JwksControllerIntegrationTest : AbstractSpringIntegrationTest() {
    @Test
    fun `returns a JWK set with exactly one public RSA key`() {
        val result = mvc.perform(MockMvcRequestBuilders.get("/jwks"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn()

        val parsed = JsonWebKeySet(result.response.contentAsString)
        assertThat(parsed.jsonWebKeys).hasSize(1)
        assertThat(parsed.jsonWebKeys[0].keyType).isEqualTo("RSA")
        assertThat(parsed.jsonWebKeys[0].key).isNotNull
        assertThat(parsed.jsonWebKeys[0].key.encoded).isNotNull()
        assertThat(parsed.jsonWebKeys[0].key.encoded).isEqualTo(getSigningKeys.keyPair.public.encoded)
    }
}
