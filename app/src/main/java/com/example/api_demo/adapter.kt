package com.example.api_demo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.api_demo.databinding.ItemNewsArticleBinding

class NewsAdapter(
    private val articles: MutableList<Article> = mutableListOf()
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    fun updateArticles(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged() // Consider using DiffUtil for better performance
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount() = articles.size

    class NewsViewHolder(
        private val binding: ItemNewsArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                titleTextView.text = article.title
                descriptionTextView.text = article.description
                sourceTextView.text = article.source.name

                val imageUrl = article.urlToImage?.split("?")?.firstOrNull()
                Log.d("NewsAdapter", "Original URL: ${article.urlToImage}")
                Log.d("NewsAdapter", "Cleaned URL: $imageUrl")

                articleImageView.load(imageUrl) {
                    crossfade(true)
                    crossfade(300)
                    placeholder(R.drawable.placeholder_news)
                    error(R.drawable.placeholder_news)
                    fallback(R.drawable.placeholder_news)
                    listener(
                        onStart = {
                            Log.d("NewsAdapter", "Started loading image from: $imageUrl")
                        },
                        onError = { _, error ->
                            Log.e("NewsAdapter", "Error loading image: ${error.throwable.message}")
                            error.throwable.printStackTrace()
                        },
                        onSuccess = { _, _ ->
                            Log.d("NewsAdapter", "Successfully loaded image")
                        }
                    )
                }
            }
        }
    }
}