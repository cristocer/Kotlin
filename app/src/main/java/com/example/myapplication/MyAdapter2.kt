package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class MyAdapter2 (private val imageModelArrayList: MutableList<String>) : RecyclerView.Adapter<MyAdapter2.ViewHolder>() {

    lateinit var mClickListener: ClickListener

    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }

    interface ClickListener {
        fun onClick(pos: Int, aView: View)
    }

    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout), View.OnClickListener {
        override fun onClick(v: View) {
            mClickListener.onClick(adapterPosition, v)
        }

        //var imgView: ImageView
        var txtMsg: TextView

        init {
            //imgView = layout.findViewById<View>(R.id.icon) as ImageView
            txtMsg = layout.findViewById<View>(R.id.firstLine) as TextView

            itemView.setOnClickListener(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.row_layout2, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]

        //holder.imgView.setImageResource(info.getImage_drawables())
        holder.txtMsg.setText(info)
    }

    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }
}