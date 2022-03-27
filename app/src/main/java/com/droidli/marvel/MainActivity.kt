package com.droidli.marvel

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.droidli.marvel.databinding.ActivityMainBinding
import com.droidli.marvel.presentation.CharactersList.CharacterListAdapter
import com.droidli.marvel.presentation.CharactersList.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), SearchView.OnQueryTextListener {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()
    private val mAdapter by lazy { CharacterListAdapter() }
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var searchTerm: String

    var flag = 3
    var paginatedValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        recyclerView()
    }

    private fun recyclerView() {
        binding.characterRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1) {
                    paginatedValue += 20
                    viewModel.getAllCharactersData(paginatedValue)
                    callApi()
                }
            }
        })

        binding.characterRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    private fun callApi() {
        lifecycle.coroutineScope.launchWhenCreated {
            repeat(flag) {
                viewModel.marvelValue.collect {
                    when {
                        it.isLoading -> {
                            binding.progressCircular.isVisible = true
                        }
                        it.error.isNotBlank() -> {
                            binding.progressCircular.isVisible = false
                            flag = 0
                            Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                        }
                        it.characterList.isNotEmpty() -> {
                            binding.progressCircular.isVisible = false
                            flag = 0
                            mAdapter.differ.submitList(it.characterList)
                        }
                    }
                }
                delay(1000)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu?.findItem(R.id.menuSearch)
        val searchView = search?.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchTerm = query
        }
        if (searchTerm.isNotEmpty()) {
            search()
        }
        return true
    }


    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchTerm = newText
        }
        if (searchTerm.isNotEmpty()) {
            search()
        }
        return true
    }

    private fun search() {
        viewModel.getSearchedCharacters(searchTerm)
        lifecycle.coroutineScope.launchWhenCreated {
            viewModel.marvelValue.collect {
                when {
                    it.isLoading -> {
                        binding.progressCircular.isVisible = true
                    }
                    it.error.isNotBlank() -> {
                        binding.progressCircular.isVisible = false
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_SHORT).show()
                    }
                    it.characterList.isNotEmpty() -> {
                        binding.progressCircular.isVisible = false
                        mAdapter.differ.submitList(it.characterList)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getAllCharactersData(paginatedValue)
        callApi()
    }
}