package com.example.pictures

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import coil.load
import androidx.recyclerview.widget.RecyclerView
import com.example.pictures.models.PictureModel

class MainAdapter : RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: MutableList<PictureModel> = arrayListOf()

    fun setData(data: MutableList<PictureModel>) {
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainAdapter.RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.pic_item, parent, false) as View)
    }

    override fun onBindViewHolder(holder: MainAdapter.RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind (data: PictureModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                val thisView = itemView.findViewById<ImageView>(R.id.image)
                thisView.load(data.url) {
                    crossfade(true)
                    placeholder(R.drawable.ic_launcher_foreground)
                }
            }
        }
    }
}