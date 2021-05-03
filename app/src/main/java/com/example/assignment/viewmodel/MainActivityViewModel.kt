package com.example.assignment.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.assignment.model.Content_
import com.example.assignment.model.PostDataSource
import kotlinx.coroutines.flow.Flow

class MainActivityViewModel(val context: Context) : ViewModel() {


   fun getPagedMovieList(filterText : String?): Flow<PagingData<Content_>> {
       return Pager(PagingConfig(pageSize = 20, prefetchDistance = 1)) {

           PostDataSource(context,filterText)
       }.flow.cachedIn(viewModelScope)
    }
}