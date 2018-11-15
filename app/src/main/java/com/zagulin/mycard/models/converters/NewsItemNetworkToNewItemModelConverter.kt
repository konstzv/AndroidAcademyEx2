package com.zagulin.mycard.models.converters

import com.zagulin.mycard.models.Category
import com.zagulin.mycard.models.ModelConverter
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.models.NewsItemNetwork
import org.joda.time.format.ISODateTimeFormat
import javax.inject.Inject


open class NewsItemNetworkToNewItemModelConverter @Inject constructor() :
        ModelConverter<NewsItemNetwork, NewsItem>
        {


    enum class ImageFormats(val formatName: String) {
        LargeImage("superJumbo")
        , Thumbnail("Standard Thumbnail")
    }


    override fun convert(item: NewsItemNetwork): NewsItem {

        var url: String? = null
        var thumbnailUrl: String? = null

        (item.multimedia).run {
            if (this != null && this.isNotEmpty()) {
                url = first { it?.format == ImageFormats.LargeImage.formatName }?.url
                thumbnailUrl = first { it?.format == ImageFormats.Thumbnail.formatName }?.url
            }
        }







        return NewsItem(
                title = item.title,
                previewText = item.abstract,
                fullText = item.abstract,
                imageUrl = url,
                thumbnailUrl = thumbnailUrl,
                publishDate = ISODateTimeFormat
                        .dateTimeParser()
                        .withOffsetParsed()
                        .parseDateTime(item.publishedDate)
                        .toDate())
    }

}