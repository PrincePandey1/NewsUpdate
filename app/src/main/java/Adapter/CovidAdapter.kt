package Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.android.newsupdate.R
import dataclass.StatewiseItem

class CovidAdapter(val context: Context, val article: List<StatewiseItem>): RecyclerView.Adapter<CovidAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var stateTv = itemView.findViewById<TextView>(R.id.state_Tv)
        var confirmedTv = itemView.findViewById<TextView>(R.id.confirmed_Tv)
        var activeTv = itemView.findViewById<TextView>(R.id.active_Tv)
        var recoveredTv = itemView.findViewById<TextView>(R.id.recovered_Tv)
        var deceasedTv = itemView.findViewById<TextView>(R.id.deceased_Tv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.covid_items,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = article[position]

        holder.activeTv.text = item.active
        holder.stateTv.text = item.state
        holder.recoveredTv.text = item.recovered
        holder.deceasedTv.text = item.deaths
        holder.confirmedTv.text = item.confirmed


    }

    override fun getItemCount(): Int {
        return article.size
    }

}

