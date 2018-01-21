package com.rodvar.cwbprep.data

import io.reactivex.Observable

interface Repository<T> {
    fun add(item: T)

    fun update(item: T)

    fun remove(item: T)

    fun getAll(): Observable<List<T>>
    fun get(): Observable<T>
}