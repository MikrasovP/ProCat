package vsu.csf.procat.ui.rentstationslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
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

    private val compositeDisposable = CompositeDisposable()

    fun updateData() {
        isLoadingFromServer.value = true
        rentStationsRepo.getRentStationsList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                rentStations.value = it
                isLoadingFromServer.value = false
                networkError.value = false
            }, { ex ->
                Timber.e(ex, "Error while getting retrieving stations list")
                isLoadingFromServer.value = false
                networkError.value = true
            }).also { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}