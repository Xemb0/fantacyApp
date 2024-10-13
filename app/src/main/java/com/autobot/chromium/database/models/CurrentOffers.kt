package com.autobot.chromium.database.models

import kotlinx.serialization.Serializable

@Serializable
data class CurrentOffers(
    val offer_list: List<Offer>
)
@Serializable
data class Offer(
    val offer_banner_url: String,
    val type: String
)