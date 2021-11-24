package com.thirdwayv.rawg.shared.store.models.enums

enum class Exceptions(val displayName: String) {
    TimeOutError("Timeout exception"),
    ServerError("Server error"),
    NetworkError("Network error"),
    UnknownError("Unknown error"),
    BadRequestError("Bad request error"),
    ForbiddenError("Forbidden error"),
}