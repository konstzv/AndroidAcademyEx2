package com.zagulin.mycard

import java.io.Serializable
import java.util.*

class NewsItem(
        val title: String? = null,
        val imageUrl: String? = null,
        val category: Category? = null,
        val publishDate: Date? = null,
        val previewText: String? = null,
        val fullText: String? = null
) : Serializable {
    companion object {
        private val serialVersionUid: Long = 0
    }
}