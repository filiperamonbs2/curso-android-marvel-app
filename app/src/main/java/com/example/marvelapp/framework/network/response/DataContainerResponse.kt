package com.example.marvelapp.framework.network.response

import com.google.gson.annotations.SerializedName

data class DataContainerResponse(
    @SerializedName( value = "results") val characters: List<CharacterResponse>
)
