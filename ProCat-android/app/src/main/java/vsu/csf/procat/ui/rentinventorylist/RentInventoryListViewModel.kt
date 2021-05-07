package vsu.csf.procat.ui.rentinventorylist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import timber.log.Timber
import vsu.csf.procat.model.RentInventory
import vsu.csf.procat.repo.RentStationsRepo
import javax.inject.Inject

@HiltViewModel
class RentInventoryListViewModel @Inject constructor(
    private val rentStationsRepo: RentStationsRepo,
) : ViewModel() {

    var rentStationId: Long? = null

    val rentInventoryList = MutableLiveData<List<RentInventory>>(listOf())

    val isLoadingFromServer = MutableLiveData(false)
    val networkError = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()

    fun updateData() {
        isLoadingFromServer.value = true
        rentStationId?.let { id ->
            rentStationsRepo.getInventoryList(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    rentInventoryList.value = it
                    isLoadingFromServer.value = false
                    networkError.value = false
                }, { ex ->
                    Timber.e(ex, "Error while retrieving inventory list for stationId = $rentStationId")
                    isLoadingFromServer.value = false
                    networkError.value = true
                }).also { compositeDisposable.add(it) }
        } ?: throw IllegalStateException("Attempt to retrieve inventory with null station id")

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}