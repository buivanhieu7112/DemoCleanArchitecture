package com.example.democleanarchitecture.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democleanarchitecture.R
import com.example.democleanarchitecture.model.RepoItem
import com.example.democleanarchitecture.util.ItemCLickListener
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), ItemCLickListener {
    private lateinit var viewModel: MainViewModel
    private var mainAdapter = MainAdapter(this)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initData()
        setupView()
    }

    private fun initData() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getUsers()
        viewModel.getAdapter(mainAdapter)
    }

    private fun setupView() {
        recyclerView.adapter = mainAdapter
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onItemClicked(user: RepoItem) {
        Toast.makeText(this, user.name, Toast.LENGTH_SHORT).show()
    }

}
