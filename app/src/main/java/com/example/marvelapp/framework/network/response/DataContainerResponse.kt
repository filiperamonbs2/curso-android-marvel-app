package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataContainerResponse(
    val offset: Int,
    val total: Int,
    @SerializedName( value = "results") val results: List<CharacterResponse>
)
