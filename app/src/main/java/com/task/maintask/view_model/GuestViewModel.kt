package com.task.maintask.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.maintask.model.MainGuestsItem
import com.task.maintask.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GuestViewModel @Inject constructor(private val repository: Repository) :ViewModel(){

    val _guestItem = MutableLiveData<MainGuestsItem>()
    val guestItems : LiveData<MainGuestsItem>
    get() = _guestItem

    fun getGuestItems(context: Context,fileName:String){
        viewModelScope.launch {
            repository.getGuests(context,fileName)
                .onStart {
                    withContext(Dispatchers.Main){

                    }
                }.catch { error ->
                    Log.e("Errors","Errors = " + error)
                }
                .collect{
                    withContext(Dispatchers.Main){
                        _guestItem.value = it
                    }
                }
        }
    }
}