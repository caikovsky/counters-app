package com.cornershop.counterstest.presentation.ui.list


import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cornershop.counterstest.R
import com.cornershop.counterstest.databinding.ItemCounterBinding
import com.cornershop.counterstest.domain.model.Counter
import java.util.*
import kotlin.collections.ArrayList

class ListCounterAdapter(
    private val decrementOnClick: (Counter) -> Unit,
    private val incrementOnClick: (Counter) -> Unit,
    private val selectOnLongPress: (Int) -> Boolean
) :
    Filterable, ListAdapter<Counter, ListCounterAdapter.CounterViewHolder>(DIFF_CALLBACK) {
    var counterList: ArrayList<Counter> = ArrayList()
    var counterListFiltered: ArrayList<Counter> = ArrayList()
    var selectedItems = SparseBooleanArray()
    private var currentSelectedPos: Int = -1

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
        val itemBinding = ItemCounterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CounterViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CounterViewHolder, position: Int) {
        val counter = counterListFiltered[position]
        holder.bind(counter)
        if (currentSelectedPos == position) currentSelectedPos = -1
    }

    override fun getItemCount(): Int = counterListFiltered.size

    fun addData(list: List<Counter>) {
        counterList = list as ArrayList<Counter>
        counterListFiltered = counterList
        notifyDataSetChanged()
    }

    @ExperimentalStdlibApi
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                counterListFiltered = if (charString.isEmpty()) counterList else {
                    val filteredList = ArrayList<Counter>()

                    counterList
                        .filter {
                            it.title.lowercase(Locale.ROOT).contains(constraint.toString().lowercase(Locale.ROOT))
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

    fun toggleSelection(position: Int) {
        currentSelectedPos = position

        if (selectedItems.get(position)) {
            selectedItems.delete(position)
            counterListFiltered[position].selected = false
        } else {
            selectedItems.put(position, true)
            counterListFiltered[position].selected = true
        }

        notifyItemChanged(position)
    }

    inner class CounterViewHolder(private val itemBinding: ItemCounterBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(counter: Counter) {
            itemBinding.run {
                normalLayout.counterTitle.text = counter.title
                selectedLayout.counterTitle.text = counter.title
                normalLayout.counterValue.text = counter.count.toString()

                normalLayout.incrementCounter.setOnClickListener { incrementOnClick(counter) }
                normalLayout.decrementCounter.setOnClickListener { decrementOnClick(counter) }
                normalLayout.counterTitle.setOnLongClickListener { selectOnLongPress(position) }

                if (counter.selected) {
                    selectedLayout.itemCounterContainer.visibility = View.VISIBLE
                    normalLayout.itemCounterContainer.visibility = View.GONE
                } else {
                    selectedLayout.itemCounterContainer.visibility = View.GONE
                    normalLayout.itemCounterContainer.visibility = View.VISIBLE
                }

                if (counter.count == 0) {
                    toggleDecrementButtonDisabledStatus(false)
                } else {
                    toggleDecrementButtonDisabledStatus(true)
                }
            }
        }

        private fun toggleDecrementButtonDisabledStatus(isActive: Boolean) {
            itemBinding.run {
                normalLayout.decrementCounter.isClickable = isActive
                normalLayout.decrementCounter.isFocusable = isActive

                if (isActive) {
                    normalLayout.decrementCounter.drawable.setTint(root.resources.getColor(R.color.orange))
                    normalLayout.counterValue.setTextColor(root.resources.getColor(R.color.black))
                } else {
                    normalLayout.decrementCounter.drawable.setTint(root.resources.getColor(R.color.light_gray))
                    normalLayout.counterValue.setTextColor(root.resources.getColor(R.color.light_gray))
                }
            }
        }
    }
}


