import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.autobot.chromium.database.BrowserTab
import java.util.UUID

class BrowserViewModel : ViewModel() {
    private val _tabs = mutableStateListOf<BrowserTab>()
    val tabs: List<BrowserTab> get() = _tabs

    // Default to one initial tab with a home URL
    init {
        addTab("https://yourhomepage.com")
    }

    // Add a new tab
    fun addTab(url: String = "https://yourhomepage.com") {
        _tabs.add(BrowserTab(UUID.randomUUID().toString(), url))
    }

    // Remove an existing tab
    fun removeTab(tabId: String) {
        _tabs.removeAll { it.id == tabId }
    }

    // Update the URL of a particular tab
    fun updateUrl(tabId: String, newUrl: String) {
        _tabs.find { it.id == tabId }?.url = newUrl
    }
}