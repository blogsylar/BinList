package ru.macdroid.binlist.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.macdroid.binlist.model.bin.ResponeModel
import ru.macdroid.binlist.utils.Constants.END_POINT_GET


interface Client {
    @GET(END_POINT_GET)
    suspend fun getCardInfo(@Path("bin") bin: Long): Response<ResponeModel>
}