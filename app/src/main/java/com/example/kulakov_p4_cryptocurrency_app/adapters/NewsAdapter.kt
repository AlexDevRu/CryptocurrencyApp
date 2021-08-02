package com.example.kulakov_p4_cryptocurrency_app.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.news.Article
import com.example.kulakov_p4_cryptocurrency_app.R
import com.example.kulakov_p4_cryptocurrency_app.databinding.ItemArticleBinding
import com.example.kulakov_p4_cryptocurrency_app.views.fragments.NewsFragmentDirections

class NewsAdapter: PagingDataAdapter<Article, NewsAdapter.ArticleHolder>(ArticleDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val binding = DataBindingUtil.inflate<ItemArticleBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_article,
            parent,
            false
        )

        return ArticleHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        getItem(position)?.let { article -> holder.bind(article) }
    }

    inner class ArticleHolder(private val binding: ItemArticleBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            binding.apply {
                article = item
                onClick = {
                    Log.w("asd", "onClick news")
                    val action = NewsFragmentDirections.actionNewsFragmentToWebViewFragment(article?.url!!)
                    itemView.findNavController().navigate(action)
                }
                executePendingBindings()
            }
        }
    }

    class ArticleDiff : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    }
}