package vsu.csf.procat.ui.rentinventorydetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import vsu.csf.procat.model.RentInventory
import vsu.csf.procat.repo.DictionariesRepo
import vsu.csf.procat.repo.RentStationsRepo
import java.math.BigDecimal
import javax.inject.Inject

@HiltViewModel
class RentInventoryDetailViewModel @Inject constructor(
    private val rentStationsRepo: RentStationsRepo,
    private val dictionariesRepo: DictionariesRepo,
) : ViewModel() {

    private lateinit var itemUuid: String

    val dataLoaded = MutableLiveData(false)
    val typeName = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val pricePerHourString = MutableLiveData<String>()
    val availabilityStatus = MutableLiveData<String>()
    val imagePath = MutableLiveData<String>()

    private val rentStatus = MutableLiveData<Long>()
    val startRentButtonVisible = Transformations.map(rentStatus) { it == STATUS_AVAILABLE }
    val finishRentButtonVisible = Transformations.map(rentStatus) { it == STATUS_IN_RENT }
    val rentUnavailableHintVisible = Transformations.map(rentStatus) { it == STATUS_UNAVAILABLE }

    val error = MutableLiveData(false)
    val loading = MutableLiveData(false)

    fun setItemUuid(itemUuid: String?) {
        if (itemUuid == null) {
            Timber.i("Item uuid is null")
            return
        }
        this.itemUuid = itemUuid
        retrieveItemData()
    }

    private fun retrieveItemData() {
        if (!::itemUuid.isInitialized) {
            Timber.e("Attempt to get item with unknown id")
            return
        }
        loading.value = true
        rentStationsRepo.getRentInventoryByUuid(itemUuid)
            .flatMap { itemModel ->
                rentStatus.postValue(itemModel.availabilityStatusId)
                dictionariesRepo.resolveRentInventory(itemModel)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ rentInventory ->
                setUpItemData(rentInventory)
                error.value = false
                dataLoaded.value = true
                loading.value = false
            }, { ex ->
                Timber.e(ex, "Error while retrieving item data, id: $itemUuid")
                error.value = true
                loading.value = false
            })
    }

    private fun setUpItemData(rentInventory: RentInventory) {
        typeName.value = rentInventory.typeName
        name.value = rentInventory.name
        pricePerHourString.value = getPricePerHour(rentInventory.pricePerHour)
        availabilityStatus.value = rentInventory.availabilityStatus
        imagePath.value = rentInventory.pathToImage
    }

    private fun getPricePerHour(price: BigDecimal) = "$price₽ в час"

    companion object {
        private const val STATUS_AVAILABLE = 1L
        private const val STATUS_IN_RENT = 2L
        private const val STATUS_UNAVAILABLE = 3L
    }

}