package com.autobot.chromium.database

import com.autobot.chromium.database.models.UserData
import javax.inject.Inject


// TabRepositoryImpl.kt

    class Repository @Inject constructor(private val dao: UserDao) : UserRepository {
        override suspend fun addData(user: UserData) {
            dao.insert(user)
        }

        override suspend fun getAllData(): List<UserData> {
            return dao.getAllData()
        }

        override suspend fun deleteData(data: UserData) {
            dao.delete(data)
        }
    }
