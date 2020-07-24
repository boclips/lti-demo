package com.boclips.lti.demo.domain

enum class LtiMessageType(val value: String) {
    RESOURCE_LINK_REQUEST("LtiResourceLinkRequest"),
    DEEP_LINKING_REQUEST("LtiDeepLinkingRequest")
}
