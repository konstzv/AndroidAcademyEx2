package com.zagulin.mycard

import java.util.*

data class NewsItem(val title: String? = null, val imageUrl: String? = null,
                    val category: Category? = null, val publishDate: Date? = null,
                    val previewText: String? = null, val fullText: String? = null)