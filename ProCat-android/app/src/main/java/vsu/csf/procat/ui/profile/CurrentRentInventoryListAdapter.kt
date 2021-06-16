package vsu.csf.procat.ui.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vsu.csf.network.BuildConfig
import vsu.csf.procat.R
import vsu.csf.procat.databinding.ItemCurrentRentInventoryBinding
import vsu.csf.procat.model.CurrentRentInventoryDto

class CurrentRentInventoryListAdapter(
    private val onItemClickListener: (CurrentRentInventoryDto) -> Unit,
) : RecyclerView.Adapter<CurrentRentInventoryListAdapter.ViewHolder>() {

    private var items: List<CurrentRentInventoryDto> = listOf()

    fun setItems(newData: List<CurrentRentInventoryDto>) {
        val diffCallback = RentInventoryDiffCallback(oldList = items, newList = newData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newData
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rentInventoryCardBinding = DataBindingUtil.inflate<ItemCurrentRentInventoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_current_rent_inventory,
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
        private val binding: ItemCurrentRentInventoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.vm?.currentRent?. let { rentInventory ->
                    onItemClickListener(rentInventory)
                }
            }
        }

        fun bind(currentRentInventory: CurrentRentInventoryDto) {
            binding.vm = CurrentRentInventoryViewModel(currentRentInventory)
            if (currentRentInventory.rentInventory.pathToImage.isNotEmpty())
                Glide.with(binding.root)
                    .load(BuildConfig.IMAGE_SERVER_URL + currentRentInventory.rentInventory.pathToImage)
                    .centerCrop()
                    .into(binding.inventoryIv)
            binding.executePendingBindings()
        }
    }

    private class RentInventoryDiffCallback(
        private val oldList: List<CurrentRentInventoryDto>,
        private val newList: List<CurrentRentInventoryDto>,
    ) : DiffUtil.Callback() {

        override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
            oldList[oldPos].rentInventory.uuid == newList[newPos].rentInventory.uuid

        override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean {
            return (oldList[oldPos].rentInventory == newList[newPos].rentInventory &&
                    oldList[oldPos].payAmount == newList[newPos].payAmount)
        }

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size
    }
}