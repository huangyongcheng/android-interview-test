package com.suongvong.interviewtest.ui.base

enum class StatusNetwork {
    RUNNING,
    SUCCESS,
    FAILED
}

data class NetworkState private constructor(
    val status: StatusNetwork,
    val msg: String? = null) {
    companion object {
        val LOADED = NetworkState(StatusNetwork.SUCCESS)
        val LOADING = NetworkState(StatusNetwork.RUNNING)
        fun onError(msg: String?) = NetworkState(StatusNetwork.FAILED, msg)
    }
}