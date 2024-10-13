package com.autobot.chromium.database.models

import kotlinx.serialization.Serializable

@Serializable
data class FeaturedTournament(
    val tournament_list: List<Tournament>
)
@Serializable
data class Tournament(

    val tournament_id: String,
    val tournament_name: String,
    val match_list: List<Match>,
    val prev_page: Int?,
    val next_page: Int?
)
