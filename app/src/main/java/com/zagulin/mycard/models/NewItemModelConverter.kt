package com.zagulin.mycard.models

import org.joda.time.format.ISODateTimeFormat


class NewItemModelConverter : ModelConvertor<NewsItemNetwork, NewsItem> {


    override fun convert(item: NewsItemNetwork): NewsItem {


        val url = if (item.multimedia != null && item.multimedia.isNotEmpty() && item.multimedia.first() != null) {
            item.multimedia.first { it?.format == "superJumbo" }?.url
        } else null

        val result = NewsItem(

                title = item.title,
                category = Category(name = item.subsection),
                previewText = item.abstract,
                fullText = item.abstract,
                imageUrl = url,
                publishDate = ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(item.publishedDate).toDate()

        )

        result.id = result.hashCode()
        return result
    }

}