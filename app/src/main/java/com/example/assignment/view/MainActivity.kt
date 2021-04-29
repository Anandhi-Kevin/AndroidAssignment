package com.example.assignment.view

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.widget.GridLayout.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.assignment.databinding.ActivityMainBinding
import com.example.assignment.viewmodel.MainActivityViewModel
import com.example.assignment.viewmodel.MainViewModelFactory
import com.example.assignment.viewmodel.SpaceItemDecoration
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {



    companion object {
        const val MARGIN = 20//dp
    }
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieListAdapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupViewModel()

    }


    private fun setupView() {
        lifecycleScope.launch {
            viewModel.listData.collectLatest { pagedData ->
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

//        if (binding.rvList.itemDecorationCount == 1)
//            binding.rvList.removeItemDecorationAt(0)
//        binding.rvList.addItemDecoration(
//            SpaceItemDecoration(
//                dpToPx(MARGIN),
//                false
//            )
//        )
        binding.rvList.setHasFixedSize(true)

        binding.rvList.adapter = movieListAdapter

        setupView()

    }

    private fun setupViewModel() {
        val factory = MainViewModelFactory(this)
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)

        setupList()

    }

    object Config {
        var includeEdge: Boolean = true
        var layoutManager: String = "linear"
        var orientation: Int = VERTICAL
        var rtl: Boolean = false
        var spans: Int = 1

        }

        fun Context.dpToPx(dp: Int) = (resources.displayMetrics.density * dp).toInt()

}