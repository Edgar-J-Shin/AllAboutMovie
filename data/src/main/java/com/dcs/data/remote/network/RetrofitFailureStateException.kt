package com.dcs.data.remote.network

class RetrofitFailureException(error: String?, val code: Int) : Exception(error) {
}