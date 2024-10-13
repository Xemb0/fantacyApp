package com.autobot.chromium.database.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WalletSummary(
    @SerialName("total_balance") val totalBalance: Int,
    @SerialName("cash_balance") val cashBalance: Int,
    @SerialName("bonus_balance") val bonusBalance: Int
)