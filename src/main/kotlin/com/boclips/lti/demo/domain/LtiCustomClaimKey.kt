package com.boclips.lti.demo.domain

enum class LtiCustomClaimKey(val value: String) {
    DEPLOYMENT_ID("https://purl.imsglobal.org/spec/lti/claim/deployment_id"),
    TARGET_LINK_URI("https://purl.imsglobal.org/spec/lti/claim/target_link_uri"),
    MESSAGE_TYPE("https://purl.imsglobal.org/spec/lti/claim/message_type"),
    VERSION("https://purl.imsglobal.org/spec/lti/claim/version"),
    RESOURCE_LINK("https://purl.imsglobal.org/spec/lti/claim/resource_link"),
    DEEP_LINKING_SETTINGS("https://purl.imsglobal.org/spec/lti-dl/claim/deep_linking_settings")
}
