package com.thirdwayv.rawg.shared.store.dao

import com.thirdwayv.rawg.shared.store.models.objectbox.GenreModel
import com.thirdwayv.rawg.shared.store.models.objectbox.GenreModel_
import io.objectbox.BoxStore
import javax.inject.Inject

class GenreDao @Inject constructor(boxStore: BoxStore): ObjectBoxBaseDAO<GenreModel>(boxStore, GenreModel::class.java) {

    override fun upsert(vararg objs: GenreModel) {
        boxStore.runInTx {
            objs.map {
                val result = this.getBoxStoreFor().query().equal(GenreModel_.serverId, it.serverId.toLong()).build()
                result.remove()
                this.getBoxStoreFor().put(*objs)

            }
        }

    }
}