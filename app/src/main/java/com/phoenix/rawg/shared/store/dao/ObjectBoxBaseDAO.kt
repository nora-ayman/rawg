package com.phoenix.rawg.shared.store.dao

import io.objectbox.BoxStore

open class ObjectBoxBaseDAO <T> constructor(val boxStore: BoxStore, val entityClass: Class<T>) {

    fun getBoxStoreFor() = boxStore.boxFor(entityClass)

    fun getAll() = getBoxStoreFor().all

    fun deleteAll() = getBoxStoreFor().removeAll()

    open fun remove(vararg objs: T) = boxStore.runInTx { this.getBoxStoreFor().remove(*objs) }

    open fun upsert(vararg objs: T) = boxStore.runInTx { this.getBoxStoreFor().put(*objs) }
}