package com.alextsy.currencylistapp.data

import androidx.paging.PagingSource
import com.alextsy.currencylistapp.api.CurrencyApi
import retrofit2.HttpException
import java.io.IOException

private const val CURRENCY_STARTING_PAGE_INDEX = 1
private const val CURRENCY_KEY = "c4453eee5c8b8315f96f3312ffbab5f4"

class CurrencyPagingSource(
    private val currencyApi: CurrencyApi,
    private val convert: String
) : PagingSource<Int, CurrencyItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CurrencyItem> {
        val position = params.key ?: CURRENCY_STARTING_PAGE_INDEX

        return try {
            val currencies = currencyApi.getCurrencies(CURRENCY_KEY, convert, position, params.loadSize)
            //val currencies = response

            LoadResult.Page(
                data = currencies,
                prevKey = if (position == CURRENCY_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (currencies.isEmpty()) null else position + 1
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }
}