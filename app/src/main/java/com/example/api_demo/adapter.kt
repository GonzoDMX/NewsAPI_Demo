package com.example.api_demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.api_demo.databinding.ItemNewsArticleBinding

class NewsAdapter(
    private val articles: MutableList<Article> = mutableListOf()
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    fun updateArticles(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        this.notifyDataSetChanged()
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
                // Image loading would be handled here with Glide or Coil
            }
        }
    }
}