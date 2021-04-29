package com.example.assignment.model

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson

class PostDataSource(val activity: Context) :  PagingSource<Int, Content_>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content_> {
        return try {
            val nextPageNumber = params.key ?: 0
            val gson = Gson()
            val jsonfile: String = activity.assets.open("page"+nextPageNumber+".json").bufferedReader().use {it.readText()}
            val response = gson.fromJson(jsonfile,PageModel::class.java)
            LoadResult.Page(
                data = response.page.content_items.content,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.page.page_size.toInt()) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Content_>): Int? {
        TODO("Not yet implemented")
    }
}