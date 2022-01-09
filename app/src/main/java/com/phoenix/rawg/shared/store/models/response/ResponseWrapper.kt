package com.phoenix.rawg.shared.store.models.response

data class ResponseWrapper<T> (val count: Int,
                               val results: List<T>)