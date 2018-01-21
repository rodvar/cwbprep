package com.rodvar.cwbprep.domain

import com.squareup.moshi.Json

data class Transaction (

    @Json(name = "id")
    var id: String? = null,
    @Json(name = "effectiveDate")
    var effectiveDate: String? = null,
    @Json(name = "description")
    var description: String? = null,
    @Json(name = "amount")
    var amount: Double = 0.toDouble(),
    @Json(name = "atmId")
    var atmId: String? = null
) {
    fun isPending() = this.atmId == null
}