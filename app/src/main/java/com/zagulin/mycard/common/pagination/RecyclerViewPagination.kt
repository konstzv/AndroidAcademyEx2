package com.zagulin.mycard.common.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewPagination(private val loadingThreshold: Int = DEFAULT_LOADING_THRESHOLD,
                             private val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {
companion object {
    const val DEFAULT_LOADING_THRESHOLD =5
}
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val manager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = manager.itemCount
        val firstVisibleItem = manager.findFirstVisibleItemPosition()
        if (totalItemCount == 0) {
            return  // loading more feature is available if there is at least one item in list
        }
        if (totalItemCount - visibleItemCount <= firstVisibleItem + loadingThreshold) {

            loadMore.invoke()

        }
    }

}