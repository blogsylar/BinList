package ru.macdroid.binlist.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.macdroid.binlist.model.bin.ResponeModel
import ru.macdroid.binlist.model.db.DBModel
import ru.macdroid.binlist.repository.Repository

class MainViewModel : ViewModel() {

    private val repository: Repository = Repository.instance!!

    fun setHistoryOfBin(data: List<DBModel>) {
        repository.setHistoryOfBin(data)
    }

    fun setBin(bin: String) {
        repository.setBin(bin)
    }

    fun getBin() : MutableLiveData<String>  {
        return repository.getBin()
    }

    fun nullState() {
        repository.nullData()
    }

    fun requestCardData(bin: Long) {
        repository.requestCardData(bin)
    }

    fun observeCardData(): MutableLiveData<ResponeModel?> {
        return repository.observeCardData()
    }

    fun observeBinNumberData(): MutableLiveData<Long?> {
        return repository.observeBinNumberData()
    }

    fun observeHistoryData(): MutableLiveData<List<DBModel>> {
        return repository.observeHistoryEnteredBin()
    }

}