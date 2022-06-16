package com.alextsy.currencylistapp.ui.currencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alextsy.currencylistapp.databinding.LoadStateFooterBinding

class CurrencyLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<CurrencyLoadStateAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding =
            LoadStateFooterBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: LoadStateFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetryFooter.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                pbFooter.isVisible = loadState is LoadState.Loading
                btnRetryFooter.isVisible = loadState !is LoadState.Loading
                tvErrorFooter.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}