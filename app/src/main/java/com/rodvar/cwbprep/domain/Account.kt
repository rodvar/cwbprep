package com.rodvar.cwbprep.domain

import com.squareup.moshi.Json

data class Account(@Json(name = "accountName") val accountName : String,
                   @Json(name = "accountNumber") val accountNumber : String,
                   @Json(name = "available") val available : Double,
                   @Json(name = "balance") val balance : Double)