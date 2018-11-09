package com.zagulin.mycard.models

import org.joda.time.format.ISODateTimeFormat
import javax.inject.Inject


class NewItemModelConverter @Inject constructor() : ModelConvertor<NewsItemNetwork, NewsItem> {


    enum class ImageFormats(val formatName: String) {
        LargeImage("superJumbo")
        ,
        Thumbnail("Standard Thumbnail")
    }

    override fun convert(item: NewsItemNetwork): NewsItem {

        var url: String? = null
        var thumbnailUrl: String?? = null
        if (item.multimedia != null && item.multimedia.isNotEmpty()) {
            url = item.multimedia.first { it?.format == ImageFormats.LargeImage.formatName }?.url
            thumbnailUrl = item.multimedia.first { it?.format == ImageFormats.Thumbnail.formatName }?.url
        }

        val result = NewsItem(

                title = item.title,
                category = Category(name = item.subsection),
                previewText = item.abstract,
                fullText = item.abstract,
                imageUrl = url,
                thumbnailUrl = thumbnailUrl,
                publishDate = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(item.publishedDate).toDate()

        )

        result.id = result.hashCode()
        return result
    }

}