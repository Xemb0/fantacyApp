package com.autobot.chromium.database

import android.content.Context
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
    fun provideAppDatabase(@ApplicationContext context: Context) = ChromiumDataBase.getInstance(context)

    @Provides
    fun provideRecipeDao(appDatabase: ChromiumDataBase): TabDao {
        return appDatabase.tabDao()
    }


    @Provides
    @Singleton
    fun provideBrowserRepository(tabDao: TabDao): BrowserRepository {
        return BrowserRepository(tabDao)
    }
}