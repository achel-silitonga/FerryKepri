package com.example.ferrykepri.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ferrykepri.data.DummyData
import com.example.ferrykepri.data.Schedule
import com.example.ferrykepri.data.SearchQuery
import com.example.ferrykepri.data.SearchState
import com.example.ferrykepri.data.SortOption
import com.example.ferrykepri.data.Ticket
import com.example.ferrykepri.data.Validators
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Manajemen state utama aplikasi. Diinisialisasi satu kali pada level
 * AppNavHost agar data tetap hidup saat rotasi layar maupun perpindahan halaman.
 */
class FerryViewModel : ViewModel() {

    private val _searchState = MutableStateFlow<SearchState>(SearchState.Idle)
    val searchState = _searchState.asStateFlow()

    private val _favorites = MutableStateFlow<Set<Int>>(emptySet())
    val favorites = _favorites.asStateFlow()

    private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
    val tickets = _tickets.asStateFlow()

    private val _cancelledTickets = MutableStateFlow<List<Ticket>>(emptyList())
    val cancelledTickets = _cancelledTickets.asStateFlow()

    private val _sortOption = MutableStateFlow(SortOption.TIME)
    val sortOption = _sortOption.asStateFlow()

    private val _lastQuery = MutableStateFlow<SearchQuery?>(null)
    val lastQuery = _lastQuery.asStateFlow()

    /** Pesan sekali-tampil (Snackbar) dari lapisan ViewModel. */
    private val _messages = MutableSharedFlow<String>(extraBufferCapacity = 4)
    val messages = _messages.asSharedFlow()

    private var lastResults: List<Schedule> = emptyList()
    private var ticketCounter = 1

    fun search(origin: String, destination: String, date: String) {
        Validators.search(origin, destination, date)?.let { message ->
            _searchState.value = SearchState.Error(message)
            emit(message)
            return
        }

        _lastQuery.value = SearchQuery(origin, destination, date)
        viewModelScope.launch {
            _searchState.value = SearchState.Loading
            try {
                delay(600)
                lastResults = DummyData.schedules.filter {
                    it.origin == origin && it.destination == destination
                }
                publishResults()
            } catch (e: Exception) {
                _searchState.value = SearchState.Error("Terjadi kesalahan: ${e.message}")
            }
        }
    }

    fun setSortOption(option: SortOption) {
        if (_sortOption.value == option) return
        _sortOption.value = option
        if (lastResults.isNotEmpty() || _searchState.value is SearchState.Empty) {
            publishResults()
        }
    }

    private fun publishResults() {
        val sorted = when (_sortOption.value) {
            SortOption.TIME -> lastResults.sortedBy { it.departTime }
            SortOption.PRICE -> lastResults.sortedBy { it.priceAdult }
        }
        _searchState.value = if (sorted.isEmpty()) SearchState.Empty else SearchState.Success(sorted)
    }

    fun toggleFavorite(scheduleId: Int) {
        val now = _favorites.value
        _favorites.value = if (scheduleId in now) now - scheduleId else now + scheduleId
    }

    fun isFavorite(scheduleId: Int): Boolean = scheduleId in _favorites.value

    fun favoriteSchedules(): List<Schedule> = DummyData.schedules.filter { it.id in _favorites.value }

    fun calcTotal(schedule: Schedule, adults: Int, children: Int): Int =
        schedule.priceAdult * adults + schedule.priceChild * children

    fun book(
        schedule: Schedule,
        date: String,
        adults: Int,
        children: Int,
        passengerName: String = "Penumpang Dewasa"
    ): Ticket? {
        Validators.booking(adults, children)?.let {
            emit(it)
            return null
        }
        val ticket = Ticket(
            id = "FK-%04d".format(ticketCounter++),
            schedule = schedule,
            date = date,
            adults = adults,
            children = children,
            total = calcTotal(schedule, adults, children),
            passengerName = passengerName
        )
        _tickets.value = listOf(ticket) + _tickets.value
        emit("Tiket ${ticket.id} berhasil diterbitkan")
        return ticket
    }

    fun cancelTicket(ticketId: String) {
        val target = _tickets.value.firstOrNull { it.id == ticketId } ?: return
        _tickets.value = _tickets.value.filterNot { it.id == ticketId }
        _cancelledTickets.value = listOf(target) + _cancelledTickets.value
        emit("Tiket ${target.id} dibatalkan")
    }

    fun notify(message: String) = emit(message)

    private fun emit(message: String) {
        _messages.tryEmit(message)
    }
}
