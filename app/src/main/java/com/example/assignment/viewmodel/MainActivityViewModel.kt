package com.example.assignment.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.assignment.model.PostDataSource

class MainActivityViewModel(val context: Context) : ViewModel() {

    val listData = Pager(PagingConfig(pageSize = 20)) {
        PostDataSource(context)
    }.flow.cachedIn(viewModelScope)

}