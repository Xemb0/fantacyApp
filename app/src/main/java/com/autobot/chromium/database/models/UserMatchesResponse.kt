package com.autobot.chromium.database.models

import kotlinx.serialization.Serializable

@Serializable
data class UserMatches(
    val match_list: List<MatchList>
)
