package com.boclips.lti.demo.domain

data class DeepLinkingResult(val data: String, val selectedContents: List<DeepLinkingSelectedContent>)

data class DeepLinkingSelectedContent(
        val type: String,
        val url: String,
        val title: String,
        val text: String
)
