package Adapter

import android.content.Context
import android.content.Intent
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
import webview.WebBusinessActivity

class BusinessAdapter(val context: Context , val article: List<Article>): RecyclerView.Adapter<BusinessAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var newsImage = itemView.findViewById<ImageView>(R.id.newsImage)
        var newsTitle = itemView.findViewById<TextView>(R.id.newsTitle)
        var newsDescription = itemView.findViewById<TextView>(R.id.newsDescription)
        var newsAuthor = itemView.findViewById<TextView>(R.id.newsAuthor)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       var view = LayoutInflater.from(context).inflate(R.layout.adapter_items,parent,false)
             return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var article = article[position]
        holder.newsAuthor.text = article.author
        holder.newsDescription.text = article.description
        holder.newsTitle.text = article.title

        Glide.with(context)
                .load(article.urlToImage)
                .into(holder.newsImage)

        holder.itemView.setOnClickListener {
           val intent = Intent(context ,  WebBusinessActivity::class.java)
            intent.putExtra("URL" , article.url)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return article.size
    }


}