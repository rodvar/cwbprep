package com.rodvar.cwbprep.domain

import com.squareup.moshi.Json

data class Location (

    @Json(name = "lat")
    var lat: Double = 0.toDouble(),
    @Json(name = "lng")
    var lng: Double = 0.toDouble()

)