package vsu.csf.procat.ui.rentstationslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ItemRentStationCardBinding
import vsu.csf.procat.model.RentStation

class RentStationsListAdapter(
    private val onItemClickListener: (RentStation) -> Unit,
) : RecyclerView.Adapter<RentStationsListAdapter.ViewHolder>() {

    private var items: List<RentStation> = listOf()

    fun setItems(newData: List<RentStation>) {
        val diffCallback = RentStationsDiffCallback(oldList = items, newList = newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rentStationCard = DataBindingUtil.inflate<ItemRentStationCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rent_station_card,
            parent,
            false,
        )
        return ViewHolder(rentStationCard)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    inner class ViewHolder(
        private val binding: ItemRentStationCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.vm?.rentStation?.let { rentStation ->
                    onItemClickListener(rentStation)
                }
            }
        }

        fun bind(rentStation: RentStation) {
            with(binding) {
                vm = RentStationViewModel(rentStation)
                executePendingBindings()
            }
        }
    }

    private class RentStationsDiffCallback(
        private val oldList: List<RentStation>,
        private val newList: List<RentStation>,
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            oldList[oldPos].id == newList[newPos].id

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
            return (oldList[oldPos].name == newList[newPos].name &&
                    oldList[oldPos].address == newList[newPos].address)
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }
}