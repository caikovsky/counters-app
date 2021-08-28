package com.cornershop.counterstest.presentation.ui.list


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.databinding.ItemCounterBinding
import com.cornershop.counterstest.domain.model.Counter

class ListCounterAdapter :
    ListAdapter<Counter, ListCounterAdapter.CounterViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Counter>() {
            override fun areItemsTheSame(oldItem: Counter, newItem: Counter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Counter, newItem: Counter): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CounterViewHolder {
        return CounterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CounterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CounterViewHolder(private val itemBinding: ItemCounterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(counter: Counter) {
            itemBinding.run {
                counterTitle.text = counter.title
            }
        }

        companion object {
            fun create(parent: ViewGroup): CounterViewHolder {
                val itemBinding =
                    ItemCounterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return CounterViewHolder(itemBinding)
            }
        }
    }
}