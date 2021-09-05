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
    private val selectOnLongPress: (Counter) -> Boolean
) :
    Filterable, ListAdapter<Counter, ListCounterAdapter.CounterViewHolder>(DIFF_CALLBACK) {
    var counterList: ArrayList<Counter> = ArrayList()
    var counterListFiltered: ArrayList<Counter> = ArrayList()
    var isSelectableMode: Boolean = false
    var selectedList = mutableListOf<Counter>()

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

    fun deleteSelectedItem() {
        if (selectedList.isNotEmpty()) {
            counterListFiltered.removeAll { item -> item.selected }
        }

        selectedList.clear()

        notifyDataSetChanged()
    }

    inner class CounterViewHolder(private val itemBinding: ItemCounterBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(counter: Counter) {
            itemBinding.run {
                counterTitle.text = counter.title
                counterValue.text = counter.count.toString()

                incrementCounter.setOnClickListener { if (!isSelectableMode) incrementOnClick(counter) }
                decrementCounter.setOnClickListener { if (!isSelectableMode) decrementOnClick(counter) }

                if (isSelectableMode) {
                    itemCounterContainer.setOnClickListener { addSelectedItemToList(counter) }
                }

                counterTitle.setOnLongClickListener {
                    isSelectableMode = true

                    if (isSelectedItem(counter)) {
                        removeSelectedItemFromList(counter)
                        itemCounterContainer.setBackgroundColor(root.resources.getColor(R.color.main_background))
                    } else {
                        addSelectedItemToList(counter)
                        itemCounterContainer.setBackgroundColor(root.resources.getColor(R.color.orange))
                    }

                    selectOnLongPress(counter)
                }

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

        private fun isSelectedItem(counter: Counter): Boolean = selectedList.contains(counter)

        private fun removeSelectedItemFromList(counter: Counter) {
            selectedList.remove(counter)

            if (selectedList.isEmpty()) {
                isSelectableMode = false
            }
        }

        private fun addSelectedItemToList(counter: Counter) {
            if (!selectedList.contains(counter)) {
                selectedList.add(counter)
            }
        }

        private fun isActiveButton(isActive: Boolean) {
            itemBinding.run {
                decrementCounter.isClickable = isActive
                decrementCounter.isFocusable = isActive
            }
        }
    }

}