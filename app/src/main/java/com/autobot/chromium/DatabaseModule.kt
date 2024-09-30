package com.autobot.chromium

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): ChromiumDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ChromiumDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @ViewModelScoped
    fun provideUserDao(database: ChromiumDatabase): BookmarkDao {
        return database.bookmarkDao()
    }

    @Provides
    @ViewModelScoped
    fun provideBrowserRepository(): BrowserRepository {
        return BrowserRepository()
    }

}
