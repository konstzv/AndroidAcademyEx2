package com.zagulin.mycard.models.converters


import com.zagulin.mycard.models.NewsItemDb
import com.zagulin.mycard.models.NewsItemNetwork
import org.joda.time.format.ISODateTimeFormat
import javax.inject.Inject


open class NewsItemNetworkToNewItemDbModelConverter @Inject constructor() :
        ModelConverter<NewsItemNetwork, NewsItemDb>
        {


    enum class ImageFormats(val formatName: String) {
        LargeImage("superJumbo")
        , Thumbnail("Normal")
    }


    override fun convert(item: NewsItemNetwork): NewsItemDb {

        var url: String? = null
        var thumbnailUrl: String? = null

        (item.multimedia).run {
            if (this != null && this.isNotEmpty()) {
                url = first { it?.format == ImageFormats.LargeImage.formatName }?.url
                thumbnailUrl = first { it?.format == ImageFormats.Thumbnail.formatName }?.url
            }
        }

        return NewsItemDb(
                title = item.title,
                previewText = item.abstract,
                fullTextUrl = item.url,
                imageUrl = url,
                thumbnailUrl = thumbnailUrl,
                publishDate = ISODateTimeFormat
                        .dateTimeParser()
                        .withOffsetParsed()
                        .parseDateTime(item.publishedDate)
                        .toDate())
    }

}