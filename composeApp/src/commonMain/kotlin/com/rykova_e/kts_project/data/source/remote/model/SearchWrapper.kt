package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchWrapper(
    val meta: MetaData,
    @SerialName("search-results")
    val searchItems: List<SearchItem>,
)

@Serializable
data class SearchItem(
    val id: Long,
    val course: Long
)

