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

)