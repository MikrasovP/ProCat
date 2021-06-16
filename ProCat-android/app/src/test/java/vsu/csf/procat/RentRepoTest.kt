package vsu.csf.procat

import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import vsu.csf.network.api.RentApi
import vsu.csf.network.model.rent.RentStopModel
import vsu.csf.procat.model.toDto
import vsu.csf.procat.repo.DictionariesRepo
import vsu.csf.procat.repo.RentRepoImpl
import java.math.BigDecimal

class RentRepoTest {

    private lateinit var rentRepoImpl: RentRepoImpl
    private val dictionariesRepo = mockk<DictionariesRepo> {}

    @Test
    fun testRentStart() {
        // Set up:
        val rentApi = mockk<RentApi> {
            every { startRent(any()) } returns Completable.complete()
        }

        rentRepoImpl = RentRepoImpl(rentApi, dictionariesRepo)
        // Trigger:
        rentRepoImpl.startRent(ITEM_UUID)
            .test()
            .await()
        // Verify:
            .assertComplete()
    }

    @Test
    fun testRentStop() {
        // Set up:
        val rentApi = mockk<RentApi> {
            every { stopRent(any()) } returns Single.just(RENT_STOP_MODEL)
        }
        rentRepoImpl = RentRepoImpl(rentApi, dictionariesRepo)
        // Trigger:
        rentRepoImpl.pauseRent(ITEM_UUID)
            .test()
            .await()
        // Verify:
            .assertResult(RENT_STOP_MODEL.toDto())
    }

    @Test
    fun testRentPayment_correctData() {
        // Set up:
        val rentApi = mockk<RentApi> {
            every { payForRent(any()) } returns Completable.complete()
        }
        rentRepoImpl = RentRepoImpl(rentApi, dictionariesRepo)
        // Trigger:
        rentRepoImpl.payForRent(RENT_ID)
            .test()
            .await()
        // Verify:
            .assertResult(true)
    }

    @Test
    fun testRentPayment_paymentTimeOut() {
        // Set up:
        val rentApi = mockk<RentApi> {
            every { payForRent(any()) } returns Completable.error(
                HttpException(
                    Response.error<Int>(
                        400,
                        "some content".toResponseBody("plain/text".toMediaTypeOrNull())
                    )
                )
            )
        }
        rentRepoImpl = RentRepoImpl(rentApi, dictionariesRepo)
        // Trigger:
        rentRepoImpl.payForRent(RENT_ID)
            .test()
            .await()
        // Verify:
            .assertResult(false)
    }

    @Test
    fun testRentPayment_unexpectedError() {
        // Set up:
        val rentApi = mockk<RentApi> {
            every { payForRent(any()) } returns Completable.error(
                HttpException(
                    Response.error<Int>(
                        500,
                        "some content".toResponseBody("plain/text".toMediaTypeOrNull())
                    )
                )
            )
        }
        rentRepoImpl = RentRepoImpl(rentApi, dictionariesRepo)
        // Trigger:
        rentRepoImpl.payForRent(RENT_ID)
            .test()
            .await()
        // Verify:
            .assertError { true } // any error
    }

    companion object {
        private const val ITEM_UUID = "19bb7e18-8069-4d74-9c90-6d0c5043b006"
        private const val RENT_ID = 958L
        private val RENT_STOP_MODEL = RentStopModel(
            BigDecimal(100.0),
            RENT_ID,
        )
    }

}