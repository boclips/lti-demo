package com.boclips.lti.demo.testsupport

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(
    properties = ["boclips.lti.initiateLoginUrl=http://localhost:8080/v1p3/initiate-login"]
)
abstract class AbstractSpringIntegrationTest {
    @Autowired
    protected lateinit var mvc: MockMvc
}