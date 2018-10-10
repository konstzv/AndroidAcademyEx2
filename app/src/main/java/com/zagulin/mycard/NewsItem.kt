package com.zagulin.mycard

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
@Parcelize
class NewsItem(val title: String? = null, val imageUrl: String? = null,
                    val category: Category? = null, val publishDate: Date? = null,
                    val previewText: String? = null, val fullText: String? = null): Parcelable