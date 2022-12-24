package ru.macdroid.binlist.network

sealed class ResponseCode(val code: Int) {
    object SUCCESS_200 : ResponseCode(200)
}
