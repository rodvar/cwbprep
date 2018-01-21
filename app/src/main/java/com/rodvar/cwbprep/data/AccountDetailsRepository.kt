package com.rodvar.cwbprep.data

import com.rodvar.cwbprep.domain.AccountDetails
import com.rodvar.cwbprep.domain.Transaction
import io.reactivex.Observable
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi



/**
 * Created by rodvar on 21/1/18.
 */
class AccountDetailsRepository : Repository<AccountDetails> {

    override fun add(item: AccountDetails) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(item: AccountDetails) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun remove(item: AccountDetails) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAll(): Observable<List<AccountDetails>> {
        throw NotImplementedError("N/A")
    }

    override fun get(): Observable<AccountDetails> {
        return Observable.create { emitter ->
            try {
                val moshi = Moshi.Builder().build()
                val jsonAdapter = moshi.adapter<AccountDetails>(AccountDetails::class.java)
                val jsonText = ResourceReader.loadJSONFromAsset("exercise.json")
                val accountDetails = jsonAdapter.fromJson(jsonText)
                if (accountDetails == null)
                    emitter.onError(IllegalArgumentException("Failed to load data"))
                else {
                    emitter.onNext(accountDetails)
                }
                emitter.setCancellable {
                    // Cancel API call / DB read if not needed any more
                }
            } catch (e : Exception) {
                emitter.onError(e)
            }
            emitter.onComplete()
        }
    }
}