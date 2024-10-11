package com.autobot.chromium.database.di

import com.autobot.chromium.database.Repository
import com.autobot.chromium.database.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTabRepository(
        browserRepository: Repository
    ): UserRepository
}