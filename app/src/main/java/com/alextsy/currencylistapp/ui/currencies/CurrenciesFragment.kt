package com.alextsy.currencylistapp.ui.currencies

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.alextsy.currencylistapp.R
import com.alextsy.currencylistapp.databinding.PageCurrenciesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrenciesFragment : Fragment(R.layout.page_currencies) {

    private val viewModel by viewModels<CurrenciesViewModel>()

    private var _binding: PageCurrenciesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = PageCurrenciesBinding.bind(view)

        val adapter = CurrencyAdapter()

        binding.apply {
            rvCurrencies.setHasFixedSize(true)
            rvCurrencies.adapter = adapter.withLoadStateHeaderAndFooter(
                header = CurrencyLoadStateAdapter { adapter.retry() },
                footer = CurrencyLoadStateAdapter { adapter.retry() },
            )
            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.currencies.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                pbCurrencies.isVisible = loadState.source.refresh is LoadState.Loading
                rvCurrencies.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    rvCurrencies.isVisible = false
                    tvNoResults.isVisible = true
                } else {
                    tvNoResults.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}