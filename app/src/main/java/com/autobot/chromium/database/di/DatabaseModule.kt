package com.autobot.chromium.database.di

import android.content.Context
import com.autobot.chromium.database.ApiService
import com.autobot.chromium.database.Repository
import com.autobot.chromium.database.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) =
        ChromiumDataBase.getInstance(context)

    @Provides
    fun provideRecipeDao(appDatabase: ChromiumDataBase): UserDao {
        return appDatabase.userDao()
    }


    @Provides
    @Singleton
    fun provideBrowserRepository(apiService: ApiService): Repository {
        return Repository(apiService)
    }
}