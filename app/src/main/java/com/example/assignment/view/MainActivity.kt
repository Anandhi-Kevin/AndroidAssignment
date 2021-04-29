package com.example.assignment.view

import android.content.res.Configuration
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assignment.R
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.viewmodel.MainActivityViewModel
import com.example.assignment.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar.toolbar)

        setTitle("Romantic Comedy")
        setupViewModel()

    }


    private fun getMovieList(filterText : String?) {
        lifecycleScope.launch {
            viewModel.getPagedMovieList(filterText).collectLatest { pagedData ->
                movieListAdapter.submitData(pagedData)
            }
        }
    }

    private fun setupList() {
        movieListAdapter = MovieListAdapter(this)
        if (resources
                .configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        ) {
            binding.rvList.layoutManager = GridLayoutManager(this, 3)
        } else {
            binding.rvList.layoutManager = GridLayoutManager(this, 5)
        }


        binding.rvList.setHasFixedSize(true)

        binding.rvList.adapter = movieListAdapter

        getMovieList("")

    }

    private fun setupViewModel() {
        val factory = MainViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)

        setupList()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(object :  SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {

                if(newText.length > 2 || TextUtils.isEmpty(newText))
                getMovieList(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                //on submit send entire query
                return false
            }

        })

        return super.onCreateOptionsMenu(menu);
    }

}