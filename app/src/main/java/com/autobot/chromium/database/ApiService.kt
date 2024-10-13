package com.autobot.chromium.database

import com.autobot.chromium.database.models.ApiResponse
import com.autobot.chromium.database.models.HighLights
import com.autobot.chromium.database.models.UpcomingMatches
import retrofit2.http.GET

interface ApiService {
    @GET("data-pipeline/v1/mock-frontend/homepage/1")
    suspend fun getHomepageData(): ApiResponse

    @GET("data-pipeline/v1/mock-frontend/matches/featured/1")
    suspend fun getHighlights(): HighLights

    @GET("data-pipeline/v1/mock-frontend/matches/upcoming/1")
    suspend fun getUpcomingMatches(): UpcomingMatches

}