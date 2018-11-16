package com.zagulin.mycard.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity(tableName = "news_items")
data class NewsItemDb(

        var title: String? = null,
        var imageUrl: String? = null,
        var categoryId: Int? = null,
        var publishDate: Date? = null,
        var previewText: String? = null,
        var fullText: String? = null,
        var thumbnailUrl: String? = null
){
        @PrimaryKey(autoGenerate = true)
        var id: Int = hashCode()


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