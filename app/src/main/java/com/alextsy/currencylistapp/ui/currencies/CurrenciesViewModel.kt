package com.alextsy.currencylistapp.ui.currencies

import androidx.hilt.Assisted
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.alextsy.currencylistapp.data.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrenciesViewModel @Inject constructor(
    private val repository: CurrencyRepository,
    private val  state: SavedStateHandle
) : ViewModel() {

    private val currentConvert = state.getLiveData(CURRENT_CONVERT, DEFAULT_CONVERT)

    val currencies = currentConvert.switchMap {
        repository.getCurrencyResults(it).cachedIn(viewModelScope)
    }

    companion object {
        private const val DEFAULT_CONVERT = "USD"
        private const val CURRENT_CONVERT = "current_convert"
    }
}