package com.mityushovn.imageviewerrestapigooglecourse.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty
import com.mityushovn.imageviewerrestapigooglecourse.network.Repository
import kotlinx.coroutines.*

enum class MarsApiStatus {
    LOADING,
    ERROR,
    DONE
}

class OverviewViewModel : ViewModel() {
    private val repository = Repository.get()

    private val _status = MutableLiveData<MarsApiStatus>()

    val status: LiveData<MarsApiStatus>
        get() {
            return _status
        }

    private val _data = MutableLiveData<List<MarsProperty>>()

    val data: LiveData<List<MarsProperty>>
        get() {
            return _data
        }

    init {
        _data.value = emptyList()
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            _status.value = MarsApiStatus.LOADING
            /* debug
            delay(2000)
            */
            try {
                _data.value = repository.getProperties()
                _status.value = MarsApiStatus.DONE
            } catch (e: Exception) {
                _status.value = MarsApiStatus.ERROR
            }
        }
    }
}