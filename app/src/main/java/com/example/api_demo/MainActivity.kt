package com.example.api_demo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import coil.Coil
import coil.ImageLoader
import coil.imageLoader
import coil.util.DebugLogger
import com.example.api_demo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        // Replace with your actual API key
        viewModel.fetchNews("YOUR_API_KEY")
    }

    private fun setupRecyclerView() {
        binding.newsRecyclerView.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun observeViewModel() {
        viewModel.newsState.observe(this) { state ->
            when (state) {
                is NewsState.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is NewsState.Success -> {
                    binding.progressBar.isVisible = false
                    newsAdapter.updateArticles(state.articles)
                }
                is NewsState.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}