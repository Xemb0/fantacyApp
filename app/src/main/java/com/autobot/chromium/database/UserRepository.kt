package com.autobot.chromium.database

import com.autobot.chromium.database.models.UserData

interface UserRepository {
    suspend fun addData(user: UserData)
    suspend fun getAllData(): List<UserData>
    suspend fun deleteData(user: UserData)
}