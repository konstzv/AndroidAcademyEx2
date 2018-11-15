package com.zagulin.mycard.models

import java.util.*

data class NewsItem(
        var id: Int? = null,
        var title: String? = null,
        val imageUrl: String? = null,
        var category: Category? = null,
        val publishDate: Date? = null,
        val previewText: String? = null,
        val fullText: String? = null,
        val thumbnailUrl: String? = null
) : FeedItem {
    init {
        if (id == null) {
            id = hashCode()
        }
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsItem

        return id == other.id


    }

    @Suppress("detekt.MagicNumber")
    override fun hashCode(): Int {
        var result = (title?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (publishDate?.hashCode() ?: 0)
        result = 31 * result + (previewText?.hashCode() ?: 0)
        result = 31 * result + (fullText?.hashCode() ?: 0)
        return result
    }
}