package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items : ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.titleVw.text = currentItem.title
        holder.content.text = currentItem.content
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.imagevw)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews : ArrayList<News>)
    {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
{
    val titleVw : TextView = itemView.findViewById(R.id.title)
    val imagevw : ImageView = itemView.findViewById(R.id.image)
    val content : TextView = itemView.findViewById(R.id.inshort_content)

}
interface NewsItemClicked {
    fun onItemClicked(item : News)
}




