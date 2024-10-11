package com.autobot.chromium.database
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.autobot.chromium.database.models.UserData

// TabDao.kt


// TabDao.kt
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tab: UserData)

    @Query("SELECT * FROM user")
    suspend fun getAllData(): List<UserData>

    @Delete
    suspend fun delete(tab: UserData)
}
