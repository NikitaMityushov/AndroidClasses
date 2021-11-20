package com.mityushovn.imageviewerrestapigooglecourse.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mityushovn.imageviewerrestapigooglecourse.models.MarsProperty
import com.mityushovn.imageviewerrestapigooglecourse.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class OverviewViewModel : ViewModel() {
    private val repository = Repository.get()

    private val _data = MutableLiveData<List<MarsProperty>>()
    
    val data: LiveData<List<MarsProperty>>
        get() {
            return _data
        }

    init {
        _data.value = getData()
    }

    private fun getData(): List<MarsProperty>? {
        var data: List<MarsProperty>? = null
        runBlocking {
            withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
                data = repository.getProperties()
            }
        }
        return data
    }
}