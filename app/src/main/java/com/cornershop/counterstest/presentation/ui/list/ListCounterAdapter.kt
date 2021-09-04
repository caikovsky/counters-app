package com.cornershop.counterstest.presentation.ui.list


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.ItemCounterBinding
import com.cornershop.counterstest.domain.model.Counter

class ListCounterAdapter(
    private val decrementOnClick: (Counter) -> Unit,
    private val incrementOnClick: (Counter) -> Unit,
) :
    Filterable, ListAdapter<Counter, ListCounterAdapter.CounterViewHolder>(DIFF_CALLBACK) {
    var counterList: ArrayList<Counter> = ArrayList()
    var counterListFiltered: ArrayList<Counter> = ArrayList()

    init {
        counterListFiltered = counterList
    }

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
        return CounterViewHolder.create(parent, decrementOnClick, incrementOnClick)
    }

    override fun onBindViewHolder(holder: CounterViewHolder, position: Int) {
        holder.bind(counterListFiltered[position])
    }

    override fun getItemCount(): Int = counterListFiltered.size

    fun addData(list: List<Counter>) {
        counterList = list as ArrayList<Counter>
        counterListFiltered = counterList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                counterListFiltered = if (charString.isEmpty()) counterList else {
                    val filteredList = ArrayList<Counter>()
                    counterList
                        .filter {
                            it.title.contains(constraint!!)
                        }
                        .forEach { filteredList.add(it) }
                    filteredList
                }

                return FilterResults().apply { values = counterListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                counterListFiltered = results?.values as ArrayList<Counter>
                notifyDataSetChanged()
            }

        }
    }

    class CounterViewHolder(
        private val itemBinding: ItemCounterBinding,
        private val decrementOnClick: (Counter) -> Unit,
        private val incrementOnClick: (Counter) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(counter: Counter) {
            itemBinding.run {
                counterTitle.text = counter.title
                counterValue.text = counter.count.toString()
                incrementCounter.setOnClickListener { incrementOnClick(counter) }
                decrementCounter.setOnClickListener { decrementOnClick(counter) }

                if (counter.count == 0) {
                    isActiveButton(false)
                    decrementCounter.drawable.setTint(root.resources.getColor(R.color.light_gray))
                    counterValue.setTextColor(root.resources.getColor(R.color.light_gray))
                } else {
                    isActiveButton(true)
                    decrementCounter.drawable.setTint(root.resources.getColor(R.color.orange))
                    counterValue.setTextColor(root.resources.getColor(R.color.black))
                }
            }
        }

        private fun isActiveButton(isActive: Boolean) {
            itemBinding.run {
                decrementCounter.isClickable = isActive
                decrementCounter.isFocusable = isActive
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                decrementOnClick: (Counter) -> Unit,
                incrementOnClick: (Counter) -> Unit
            ): CounterViewHolder {
                val itemBinding =
                    ItemCounterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return CounterViewHolder(itemBinding, decrementOnClick, incrementOnClick)
            }
        }
    }

}