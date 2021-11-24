package com.thirdwayv.rawg.shared.constants

object Constants {
    const val API_KEY = "8aaf49327c7840ca809d6377181bde12"
    const val HEADER_API_KEY = "key"
    const val BASE_URL = "https://api.rawg.io/api/"

    const val GENRES = "genres"
    const val GAMES = "games"
    const val GAME_DETAILS = "${GAMES}/{game_id}"
    const val GAME_TRAILERS = "${GAME_DETAILS}/movies"
    const val GAME_SCREENSHOTS = "${GAME_DETAILS}/screenshots"


    const val SHARED_PREFERENCES = "THIRDWAYV_RAWG_PREFS"
    const val IS_INITIAL_INSTALLATION = "IS_INITIAL_INSTALLATION"

    const val PLAYER_POSITION = "genres"

}