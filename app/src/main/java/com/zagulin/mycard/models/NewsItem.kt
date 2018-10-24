package com.zagulin.mycard.models

import java.io.Serializable
import java.util.*

class NewsItem(
        val id:Int,
        val title: String? = null,
        val imageUrl: String? = null,
        val category: Category? = null,
        val publishDate: Date? = null,
        val previewText: String? = null,
        val fullText: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 0
    }
}