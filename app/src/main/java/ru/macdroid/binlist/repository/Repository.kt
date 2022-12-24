package ru.macdroid.binlist.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.macdroid.binlist.model.bin.ResponeModel
import ru.macdroid.binlist.model.db.DBModel
import ru.macdroid.binlist.network.ResponseCode
import ru.macdroid.binlist.network.RetofitInstance

class Repository : ViewModel() {

    private val cardModel: MutableLiveData<ResponeModel?> = MutableLiveData()
    private val binNumberSuccess: MutableLiveData<Long?> = MutableLiveData()
    private val historyEnteredBin = MutableLiveData<List<DBModel>>()
    private val _bin = MutableLiveData<String>()

    fun setBin(bin: String) {
        _bin.value = bin
    }

    fun getBin() : MutableLiveData<String>  {
        return _bin
    }

    fun nullData() {
        cardModel.postValue(null)
        binNumberSuccess.postValue(null)
    }

    fun setHistoryOfBin(data: List<DBModel>) {
        historyEnteredBin.value = data
    }

    fun observeHistoryEnteredBin(): MutableLiveData<List<DBModel>> {
        return historyEnteredBin
    }

    fun observeCardData(): MutableLiveData<ResponeModel?> {
        return cardModel
    }

    fun observeBinNumberData(): MutableLiveData<Long?> {
        return binNumberSuccess
    }

    fun requestCardData(bin: Long) {
        viewModelScope.launch {
            val response = RetofitInstance.makeRetrofitService().getCardInfo(bin)

            println("-----------------------------------------")
            println("bin $bin")
            println("cardModel.value ${cardModel.value}")
            println("response body ${response.body()}")
            println("response $response")
            println("response isSuccessful ${response.isSuccessful}")
            println("response code ${response.code()}")
            println("-----------------------------------------")

            try {
                withContext(Dispatchers.Main) {
                    when (response.code()) {
                        ResponseCode.SUCCESS_200.code -> {
                            if (response.isSuccessful) {
                                response.body()?.let {
                                    cardModel.postValue(response.body())
                                    binNumberSuccess.postValue(bin)
                                }
                            }
                        }
                        else -> nullData()
                    }
                }
            } catch (e: HttpException) {
                Log.d(TAG, "${e.message}")
            } catch (e: Throwable) {
                Log.d(TAG, "${e.message}")
            }
        }
    }

    companion object {
        private var notworkRepository: Repository? = null
        val instance: Repository?
            get() {
                if (notworkRepository == null) {
                    notworkRepository = Repository()
                }
                return notworkRepository
            }
    }
}