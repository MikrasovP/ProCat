package vsu.csf.procat.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import vsu.csf.network.api.RentApi
import vsu.csf.network.model.rent.RentDtoModel
import vsu.csf.network.model.rent.RentPaymentModel
import vsu.csf.procat.model.RentPauseDto
import javax.inject.Inject

class RentRepoImpl @Inject constructor(
    private val rentApi: RentApi,
) : RentRepo {

    override fun startRent(itemUuid: String): Completable =
        rentApi.startRent(RentDtoModel(itemUuid))
            .subscribeOn(Schedulers.io())

    override fun pauseRent(itemUuid: String): Single<RentPauseDto> =
        rentApi.stopRent(RentDtoModel(itemUuid))
            .map { RentPauseDto.fromModel(it) }
            .subscribeOn(Schedulers.io())

    override fun payForRent(rentId: Long): Single<Boolean> =
        rentApi.payForRent(RentPaymentModel(rentId))
            .toSingleDefault(true)
            .onErrorResumeNext { ex ->
                if (ex is HttpException && ex.code() == RENT_PAYMENT_ERROR_CODE)
                    Single.just(false)
                else
                    Single.error(ex)
            }.subscribeOn(Schedulers.io())

    companion object {
        private const val RENT_PAYMENT_ERROR_CODE = 400
    }

}