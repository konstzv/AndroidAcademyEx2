package com.zagulin.mycard

class LocalFeedRepository : FeedRepository {
    override fun getNewsWithAds(): List<Any> {
        val data = DataUtils.generateNews().toMutableList<Any>()
        if (data.isNotEmpty()) {
            data.add(1, String())
        }
        return data
    }

    override fun getNews(): List<NewsItem> {
        return DataUtils.generateNews()


    }


}