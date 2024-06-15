package com.example.data.remote.network

class RetrofitFailureException(error: String?, val code: Int) : Exception(error) {
}