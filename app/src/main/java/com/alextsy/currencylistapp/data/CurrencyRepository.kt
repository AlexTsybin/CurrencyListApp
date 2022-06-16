package com.alextsy.currencylistapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.alextsy.currencylistapp.api.CurrencyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject constructor(private val currencyApi: CurrencyApi) {

    fun getCurrencyResults(convert: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CurrencyPagingSource(currencyApi, convert) }
        ).liveData
}