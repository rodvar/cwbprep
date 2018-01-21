package com.rodvar.cwbprep.domain

import com.squareup.moshi.Json

data class Atm (

    @Json(name = "id")
    var id: String? = null,
    @Json(name = "name")
    var name: String? = null,
    @Json(name = "address")
    var address: String? = null,
    @Json(name = "location")
    var location: Location? = null

)