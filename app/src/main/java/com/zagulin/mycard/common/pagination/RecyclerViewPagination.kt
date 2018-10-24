package com.zagulin.mycard.common.pagination

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewPagination(private val loadingThreshold: Int = 5,
                             private val loadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    /**
     * Позволяет не дергать оверхед загрузок
     * во время подгрузки следующей страницы
     */
    private var isLoading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val manager = recyclerView.layoutManager as? LinearLayoutManager ?: return
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = manager.itemCount
        val firstVisibleItem = manager.findFirstVisibleItemPosition()
        if (totalItemCount == 0) {
            return  // loading more feature is available if there is at least one item in list
        }
        if (!isLoading && totalItemCount - visibleItemCount <= firstVisibleItem + loadingThreshold) {
            isLoading = true
            loadMore.invoke()
            isLoading = false
        }
    }

}