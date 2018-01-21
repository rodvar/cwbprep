package com.rodvar.cwbprep.domain

import com.squareup.moshi.Json

data class AccountDetails (

    @Json(name = "account")
    var account: Account? = null,
    @Json(name = "transactions")
    var transactions: List<Transaction>? = null,
    @Json(name = "pending")
    var pending: List<Transaction>? = null,
    @Json(name = "atms")
    var atms: List<Atm>? = null

) {

    /**
     * compiles all the transactions sorted by desc date
     */
    fun transactionsSortedByDescendingDate() : List<Transaction> {
        val list = this.transactions?.toMutableList()
        if (this.pending != null)
            list?.addAll(this.pending!!)
        if (list == null)
            return listOf()
        else
            return list.sortedByDescending { it.effectiveDate }
    }

    fun transactionsSize(): Int {
        return transactionsSortedByDescendingDate().size
    }
}