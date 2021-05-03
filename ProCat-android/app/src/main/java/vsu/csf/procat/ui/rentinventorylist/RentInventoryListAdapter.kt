package vsu.csf.procat.ui.rentinventorylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ItemRentInventoryCardBinding
import vsu.csf.procat.model.RentInventory

class RentInventoryListAdapter(
    private val onItemClickListener: (RentInventory) -> Unit,
) : RecyclerView.Adapter<RentInventoryListAdapter.ViewHolder>() {

    private var items: List<RentInventory> = listOf()

    fun setItems(newData: List<RentInventory>) {
        val diffCallback = RentInventoryDiffCallback(oldList = items, newList = newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rentInventoryCardBinding = DataBindingUtil.inflate<ItemRentInventoryCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_rent_inventory_card,
            parent,
            false,
        )
        return ViewHolder(rentInventoryCardBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.count()

    inner class ViewHolder(
        private val binding: ItemRentInventoryCardBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.vm?.rentInventory?.let { rentInventory ->
                    onItemClickListener(rentInventory)
                }
            }
        }

        fun bind(rentInventory: RentInventory) {
            binding.vm = RentInventoryViewModel(rentInventory)
            if (rentInventory.pathToImage.isNotEmpty())
                Glide.with(binding.root)
                    .load(rentInventory.pathToImage)
                    .centerCrop()
                    .into(binding.inventoryIv)
            binding.executePendingBindings()
        }
    }

    private class RentInventoryDiffCallback(
        private val oldList: List<RentInventory>,
        private val newList: List<RentInventory>,
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            oldList[oldPos].id == newList[newPos].id

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
            return (oldList[oldPos].name == newList[newPos].name &&
                    oldList[oldPos].pathToImage == newList[newPos].pathToImage &&
                    oldList[oldPos].pricePerHour == newList[newPos].pricePerHour &&
                    oldList[oldPos].typeName == newList[newPos].typeName &&
                    oldList[oldPos].availabilityStatus == newList[newPos].availabilityStatus)
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }
}