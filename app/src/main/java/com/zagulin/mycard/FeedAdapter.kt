package com.zagulin.mycard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.SimpleDateFormat

private val simpleDateFormat = SimpleDateFormat("yyyy.MM")

class FeedAdapter(val items: List<Any>) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val TYPE_NEWS = 0
        const val TYPE_AD = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == TYPE_NEWS)
            return NewsHolder(inflater.inflate(R.layout.item_news, parent, false))
        return AdHolder(inflater.inflate(R.layout.item_ad, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position] is String) {
            return TYPE_AD
        }
        return TYPE_NEWS
    }

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if (item is NewsItem) {
            (holder as NewsHolder).bind(item)
        }


    }


}


open class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

class AdHolder(view: View) : ViewHolder(view)
class NewsHolder(view: View) : ViewHolder(view) {


    private val title: TextView = view.item_news_layout_title as TextView
    private val imageView: ImageView = view.item_news_layout_recycler_img as ImageView
    private val subTitle: TextView = view.item_news_subtitle as TextView
    private val date: TextView = view.item_news_layout_recycler_date as TextView
    private val body: TextView = view.item_news_layout_recycler_context as TextView

    fun bind(item: NewsItem) {
        title.text = item.title
        body.text = item.previewText
        date.text = simpleDateFormat.format(item.publishDate)
        item.category?.name.let {
            subTitle.text = it
        }
        Glide.with(imageView).load(item.imageUrl).into(imageView)
    }


}