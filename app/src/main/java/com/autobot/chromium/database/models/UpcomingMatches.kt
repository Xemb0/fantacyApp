package com.autobot.chromium.database.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingMatches(
    @SerialName("match_list") val matchList: List<Match>,
    @SerialName("prev_page") val prevPage: Int? = null,
    @SerialName("next_page") val nextPage: Int? = null
)

@Serializable
data class Match(
    val id: String,
    val name: String,
    @SerialName("match_format") val matchFormat: String,
    @SerialName("tournament_name") val tournamentName: String,
    val status: String,
    @SerialName("starts_at") val startsAt: Long,
    val teams: Teams,
    @SerialName("match_offers") val matchOffers: List<MatchOffer> = emptyList(),
    val metadata: Metadata
)

@Serializable
data class Teams(
    val a: Team,
    val b: Team
)

@Serializable
data class Team(
    val name: String,
    val code: String,
    @SerialName("logo_url") val logoUrl: String,
    @SerialName("logo_bg_color") val logoBgColor: String
)

@Serializable
data class MatchOffer(
    @SerialName("offer_icon_url") val offerIconUrl: String,
    val title: String,
    val subtext: String
)

@Serializable
data class Metadata(
    @SerialName("is_lineup_out") val isLineupOut: Boolean,
    @SerialName("is_match_initialized") val isMatchInitialized: Boolean
)