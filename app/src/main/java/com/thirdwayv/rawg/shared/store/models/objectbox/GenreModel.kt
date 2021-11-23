package com.thirdwayv.rawg.shared.store.models.objectbox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
class GenreModel {

    @Id
    var id: Long = 0
    var name: String = ""
    var thumbnail: String? = null
    var serverId: Int = 0

    constructor()

    constructor(name: String, thumbnail: String?, serverId: Int) {
        this.name = name
        this.thumbnail = thumbnail
        this.serverId = serverId
    }
}