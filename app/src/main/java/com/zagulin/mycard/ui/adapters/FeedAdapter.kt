package com.zagulin.mycard.ui.adapters

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zagulin.mycard.R
import com.zagulin.mycard.common.OnNewsItemClickListener
import com.zagulin.mycard.models.NewsItem
import kotlinx.android.synthetic.main.item_news.view.*
import java.util.*


class FeedAdapter(var items: MutableList<Any> = mutableListOf(), val onNewsItemClickListener: OnNewsItemClickListener) : RecyclerView.Adapter<ViewHolder>() {


    enum class ItemTypes(val type: Int) {
        TYPE_NEWS(0),
        TYPE_AD(1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ItemTypes.TYPE_NEWS.type -> return NewsHolder(inflater.inflate(R.layout.item_news, parent, false))
            else -> AdHolder(inflater.inflate(R.layout.item_ad, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position] is String) {
            return ItemTypes.TYPE_AD.type
        }
        return ItemTypes.TYPE_NEWS.type
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun insertItem(index: Int, item: Any) {
        items.add(index, item)
        notifyItemInserted(index)
    }

    fun addItems(newItems: List<Any>) {
        val endIndex = items.size - 1
        items.addAll(newItems)
        notifyItemRangeInserted(endIndex, items.size - 1)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        if (item is NewsItem) {
            (holder as NewsHolder).bind(item, onNewsItemClickListener)
        }
    }
}

open class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

class AdHolder(view: View) : ViewHolder(view)
class NewsHolder(view: View) : ViewHolder(view) {

    private val title = view.item_news_layout_title
    private val imageView = view.item_news_layout_recycler_img
    private val subTitle = view.item_news_subtitle
    private val date = view.item_news_layout_recycler_date
    private val body = view.item_news_layout_recycler_context

    fun bind(item: NewsItem, onNewsItemClickListener: OnNewsItemClickListener) {
        title.text = item.title
        body.text = item.previewText
        item.publishDate?.let {
            date.text = DateUtils.getRelativeTimeSpanString(it.time, Calendar.getInstance().time.time,
                    0L, DateUtils.FORMAT_ABBREV_ALL)
        }

        item.category?.name.let {
            subTitle.text = it
        }
//        item.imageUrl?.let {
//            Glide.with(imageView).load(it).into(imageView)
//        }
//

        item.imageUrl?.let {
            Glide.with(imageView).load(item.imageUrl).into(imageView)
        } ?: run {
            imageView.visibility = View.GONE
        }

        itemView.setOnClickListener { onNewsItemClickListener.onItemClick(item) }
    }
}