package com.zagulin.mycard.models

import java.util.*

data class NewsItem(
        var id: Int? = null,
        val title: String? = null,
        val imageUrl: String? = null,
        val category: Category? = null,
        val publishDate: Date? = null,
        val previewText: String? = null,
        val fullText: String? = null,
        val thumbnailUrl: String? = null
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NewsItem
        if (id != null && (id == other.id)) return true
        if (id != other.id) return false
        if (title != other.title) return false
        if (imageUrl != other.imageUrl) return false
        if (thumbnailUrl != other.thumbnailUrl) return false
        if (category != other.category) return false
        if (publishDate != other.publishDate) return false
        if (previewText != other.previewText) return false
        if (fullText != other.fullText) return false

        return true
    }

    override fun hashCode(): Int {
        var result = (title?.hashCode() ?: 0)
        result = 31 * result + (imageUrl?.hashCode() ?: 0)
        result = 31 * result + (category?.hashCode() ?: 0)
        result = 31 * result + (publishDate?.hashCode() ?: 0)
        result = 31 * result + (previewText?.hashCode() ?: 0)
        result = 31 * result + (fullText?.hashCode() ?: 0)
        return result
    }
}