package vsu.csf.procat.ui.rentstationslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import vsu.csf.procat.model.RentStation
import vsu.csf.procat.repo.RentStationsRepo
import javax.inject.Inject

@HiltViewModel
class RentStationsListViewModel @Inject constructor(
    private val rentStationsRepo: RentStationsRepo,
) : ViewModel() {

    val rentStations = MutableLiveData<List<RentStation>>(listOf())

    val isLoadingFromServer = MutableLiveData(false)
    val networkError = MutableLiveData(false)

    fun updateData() {
        isLoadingFromServer.value = true
        rentStationsRepo.getRentStationsList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                rentStations.value = it
                isLoadingFromServer.value = false
                networkError.value = false
            }, { ex ->
                Timber.e(ex)
                isLoadingFromServer.value = false
                networkError.value = false
            })
    }

    override fun onCleared() {
        super.onCleared()
    }
}