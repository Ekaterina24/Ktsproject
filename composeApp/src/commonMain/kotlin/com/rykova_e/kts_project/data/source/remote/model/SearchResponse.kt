package com.rykova_e.kts_project.data.source.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    @SerialName("meta")
    val meta: MetaDataRemote,
    @SerialName("search-results")
    val searchItems: List<SearchRemote>,
)

@Serializable
data class SearchRemote(
    @SerialName("id")
    val id: Long,
    @SerialName("course")
    val courseId: Long
)

