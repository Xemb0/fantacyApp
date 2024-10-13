package com.autobot.chromium.database.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse (
    val user_matches: UserMatches,
    val current_offers: CurrentOffers,
    val featured_tournament: FeaturedTournament? = null,
    val upcoming_matches: UpcomingMatches? = null,
    val wallet_summary: WalletSummary? = null ,
)

@Serializable
data class HighLights(
    @SerialName("match_list") val matchList: List<Match>,
    @SerialName("previous_page") val previousPage: String?,
    @SerialName("next_page") val nextPage: Int?
)
