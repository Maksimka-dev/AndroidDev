package com.pcfaktor.androiddev.ui.feed

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.pcfaktor.androiddev.R
import com.pcfaktor.androiddev.databinding.ItemFeedBinding
import com.pcfaktor.androiddev.domain.entity.Article

class FeedAdapter(
    private val onClickListener: (position: Int) -> Unit
) : ListAdapter<Article, ArticleViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_feed, parent, false)
        val holder = ArticleViewHolder(itemView)
        holder.itemView.setOnClickListener {
            onClickListener(holder.adapterPosition)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val currentItem = currentList[position]
        currentItem.run {
            holder.binding.tvItemTitle.text = title
            holder.binding.tvItemDescription.text = description
            holder.binding.ivItemImage.load(Uri.parse(image))
            holder.binding.tvItemCreator.text = creator
            holder.binding.tvItemDate.text = date
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.component1() == newItem.component1() &&
                    oldItem.component2() == newItem.component2() &&
                    oldItem.component3() == newItem.component3() &&
                    oldItem.component4() == newItem.component4() &&
                    oldItem.component5() == newItem.component5()
        }
    }
}

class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal val binding = ItemFeedBinding.bind(itemView)
}