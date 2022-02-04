package com.example.core.data.repository

import androidx.paging.PagingSource
import javax.xml.stream.events.Characters

interface CharactersRepository {

    fun getCharacters(query: String): PagingSource< Int, Characters >
}