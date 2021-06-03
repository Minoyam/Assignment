package com.mino.assignment.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class EndlessScrollListener(
    private val manager: LinearLayoutManager,
    private val lastItemListener: (Int) -> Unit

) : RecyclerView.OnScrollListener() {

    private var page = 1
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private var pastVisibleItems: Int = 0
    var loading = false
    var searchBoolean = true

    fun clear() {
        page = 1
        loading = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy < 0)
            return

        visibleItemCount = manager.childCount
        totalItemCount = manager.itemCount
        pastVisibleItems = manager.findFirstVisibleItemPosition()
        if (!loading) {
            if ((visibleItemCount + pastVisibleItems >= totalItemCount) && searchBoolean) {
                loading = true
                page++
                lastItemListener(page)
            }
        }
    }
}