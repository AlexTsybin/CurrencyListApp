package com.alextsy.currencylistapp.ui.currencies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alextsy.currencylistapp.R
import com.alextsy.currencylistapp.data.CurrencyItem
import com.alextsy.currencylistapp.databinding.ItemCurrencyBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class CurrencyAdapter : PagingDataAdapter<CurrencyItem, CurrencyAdapter.CurrencyViewHolder>(
    CURRENCY_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: CurrencyItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(currency.logo_url)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivImage)

                tvSymbol.text = "(" + currency.symbol + ")"
                tvName.text = currency.name
                tvCurrency.text = "$"
                tvPrice.text = currency.price
            }
        }
    }

    companion object {
        private val CURRENCY_COMPARATOR = object : DiffUtil.ItemCallback<CurrencyItem>() {
            override fun areItemsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: CurrencyItem, newItem: CurrencyItem) =
                oldItem == newItem
        }
    }
}