package com.example.assignment.model

import android.content.Context
import android.text.TextUtils
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.Gson
import kotlinx.coroutines.delay

class PostDataSource(val activity: Context, val filterValue: String?) :  PagingSource<Int, Content_>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content_> {
        return try {
            val nextPageNumber = params.key ?: 0

            val response = getResponse(nextPageNumber)

            LoadResult.Page(
                data = filter(filterValue,response!!.page.content_items.content),
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response!!.page.page_size.toInt()) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Content_>): Int? {
        TODO("Not yet implemented")

    }

    fun filter(text : String?, displayedList: List<Content_>) : List<Content_>{
        if(TextUtils.isEmpty(text)) return displayedList

        val temp: MutableList<Content_> = ArrayList()
        for (d in displayedList) {
            if (d.name.toLowerCase().contains(text!!.toLowerCase())) {
                temp.add(d)
            }
        }
        return temp
    }

    suspend fun getResponse(nextPageNumber : Int): PageModel? {

        delay(2000)
        val gson = Gson()
        val jsonfile: String = activity.assets.open("page"+nextPageNumber+".json").bufferedReader().use {it.readText()}
        return gson.fromJson(jsonfile,PageModel::class.java)

    }
}