package Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.newsupdate.R
import dataclass.Article

class TechAdapter(val context: Context , val article: List<Article>):RecyclerView.Adapter<TechAdapter.TechViewHolder>() {

    class TechViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
       var newsImage = itemView.findViewById<ImageView>(R.id.newsImage)
        var newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
        var newsDescription = itemView.findViewById<TextView>(R.id.newsDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.adapter_items,parent,false)
        return TechViewHolder(view)
    }

    override fun onBindViewHolder(holder: TechViewHolder, position: Int) {
       val item = article[position]
        holder.newsTitle.text = item.title
        holder.newsDescription.text = item.description

        Glide.with(context)
                .load(item.urlToImage)
                .into(holder.newsImage)

        holder.itemView.setOnClickListener {
            Toast.makeText(context, item.title , Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
       return article.size
    }


}