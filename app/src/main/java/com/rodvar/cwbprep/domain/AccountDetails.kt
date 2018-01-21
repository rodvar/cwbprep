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
    fun transactionsSize(): Int {
        var transactionsSize = this.transactions?.size
        var pendingsSize = this.pending?.size
        if (transactionsSize == null)
            transactionsSize = 0
        if (pendingsSize == null)
            pendingsSize = 0
        return transactionsSize + pendingsSize
    }
}