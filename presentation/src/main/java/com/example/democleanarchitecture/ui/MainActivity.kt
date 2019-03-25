package com.example.democleanarchitecture.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democleanarchitecture.R
import com.example.democleanarchitecture.model.RepoItem
import com.example.democleanarchitecture.util.ItemCLickListener
import com.example.democleanarchitecture.util.ItemMenuClickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), ItemCLickListener, ItemMenuClickListener {
    private lateinit var viewModel: MainViewModel
    private lateinit var searchView: SearchView
    private var mainAdapter = MainAdapter(this, this)
    private var isOnline = true

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        setupView()
        subscribeUI()
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getUsers()
    }

    private fun setupView() {
        supportActionBar!!.title = "Filter"
        recyclerView.adapter = mainAdapter
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun subscribeUI() {
        viewModel.data.observe(this, Observer {
            mainAdapter.submitList(it)
        })
    }

    override fun onItemClicked(user: RepoItem) {
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
    }

    override fun onItemMenuClick(user: RepoItem) {
        viewModel.insertUserToLocal(user)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu!!.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(applicationContext, "search $query", Toast.LENGTH_SHORT).show()
                return when (isOnline) {
                    true -> {
                        viewModel.getUserBySearch(query)
                        true
                    }
                    false -> {
                        viewModel.getUserLocalBySearch(query)
                        true
                    }
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                onBackPressed()
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_online -> {
                viewModel.getUsers()
                isOnline = true
                return true
            }
            R.id.action_offline -> {
                viewModel.getUsersLocal()
                isOnline = false
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when (isOnline) {
            true -> {
                viewModel.getUsers()
                return
            }
            false -> {
                viewModel.getUsersLocal()
                return
            }
        }
    }
}
