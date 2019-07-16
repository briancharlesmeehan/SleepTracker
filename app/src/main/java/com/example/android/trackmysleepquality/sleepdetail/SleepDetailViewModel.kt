package com.example.android.trackmysleepquality.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import com.example.android.trackmysleepquality.database.SleepNight
import kotlinx.coroutines.Job

class SleepDetailViewModel(
        private val sleepNightKey: Long = 0L,
        dataSource: SleepDatabaseDao) : ViewModel() {

    val database = dataSource

    private val viewModelJob = Job()

    private val night = MediatorLiveData<SleepNight>()

    fun getNight() = night

    init {
        night.addSource(database.getNightWithId(sleepNightKey), night::setValue)
    }

    private val _navToSleepTracker = MutableLiveData<Boolean?>()

    val navToSleepTracker: LiveData<Boolean?>
        get() =  _navToSleepTracker

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun doneNavigating() {
        _navToSleepTracker.value = null
    }

    fun onClose() {
        _navToSleepTracker.value = true
    }
}