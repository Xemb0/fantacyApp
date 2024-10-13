package com.autobot.chromium.database.models

import kotlinx.serialization.Serializable

@Serializable
data class MatchList(
    val match: Match,
    val user_contests: Int? = null,  // Nullable with default value
    val user_teams: Int? = null,      // Nullable with default value
    val top_running_rank: Long? = null // Nullable with default value
)
