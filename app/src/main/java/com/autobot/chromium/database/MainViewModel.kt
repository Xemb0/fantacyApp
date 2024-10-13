package com.autobot.chromium.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autobot.chromium.database.models.ApiResponse
import com.autobot.chromium.database.models.CurrentOffers
import com.autobot.chromium.database.models.HighLights
import com.autobot.chromium.database.models.UpcomingMatches
import com.autobot.chromium.database.models.UserMatches
import com.autobot.chromium.database.models.WalletSummary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _data = MutableStateFlow<ApiResponse?>(null)
    val data: StateFlow<ApiResponse?> get() = _data
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading
    private val _userMatches = MutableStateFlow<UserMatches?>(null)
    val userMatches: StateFlow<UserMatches?> get() = _userMatches
    private val _walletSummary = MutableStateFlow<WalletSummary?>(null)
    val walletSummary: StateFlow<WalletSummary?> get() = _walletSummary
    private val _upcomingMatches = MutableStateFlow<UpcomingMatches?>(null)
    val upcomingMatches: StateFlow<UpcomingMatches?> get() = _upcomingMatches
    private val _currentOffers = MutableStateFlow<CurrentOffers?>(null)
    val currentOffers: StateFlow<CurrentOffers?> get() = _currentOffers
    private val _featuredTournament = MutableStateFlow<HighLights?>(null)
    val featuredTournament: StateFlow<HighLights?> get() = _featuredTournament

    init {
        fetchData()
        fetchHighlights()
        fetchUpcomingMatches()
    }

    fun fetchData() {
        viewModelScope.launch {
            _isLoading.value = true // Set loading state
            try {
                val response = repository.fetchData()
                // Update your state flows
                _data.value = response
                _userMatches.value = response.user_matches
                _walletSummary.value = response.wallet_summary
                _currentOffers.value = response.current_offers
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false // Reset loading state
            }
        }
    }
    fun fetchHighlights() {
        viewModelScope.launch {
            _isLoading.value = true // Set loading state
            try {
                val response = repository.fetchHighlights()
                _featuredTournament.value = response
                // Update your state flows
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false // Reset loading state
            }
        }
    }
    fun fetchUpcomingMatches() {
        viewModelScope.launch {
            _isLoading.value = true // Set loading state
            try {
                val response = repository.fetchUpcomingMatches()
                _upcomingMatches.value = response
                // Update your state flows
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false // Reset loading state
            }
        }
    }
}
