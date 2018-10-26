package com.zagulin.mycard.models

import com.google.gson.annotations.SerializedName


data class GetTopStoriesNetworkResponse(

        @field:SerializedName("copyright")
        val copyright: String? = null,

        @field:SerializedName("last_updated")
        val lastUpdated: String? = null,

        @field:SerializedName("section")
        val section: String? = null,

        @field:SerializedName("results")
        val results: List<NewsItemNetwork?>? = null,

        @field:SerializedName("num_results")
        val numResults: Int? = null,

        @field:SerializedName("status")
        val status: String? = null
)

