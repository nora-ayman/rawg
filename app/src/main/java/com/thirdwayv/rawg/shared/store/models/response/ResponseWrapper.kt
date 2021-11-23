package com.thirdwayv.rawg.shared.store.models.response

data class ResponseWrapper<T> (val count: Int,
                               val results: List<T>)