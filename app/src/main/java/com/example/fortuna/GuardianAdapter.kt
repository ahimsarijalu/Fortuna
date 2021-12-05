package com.example.fortuna

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class GuardianAdapter(private var guardianList: ArrayList<Guardian>) :
    RecyclerView.Adapter<GuardianAdapter.MyViewHolder>() {

    private lateinit var mListener: onItemClickListener

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                mListener.onItemClicked(guardianList[adapterPosition].key, adapterPosition)
            }
        }
    }

    interface onItemClickListener {
        fun onItemClicked(key: String?, position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GuardianAdapter.MyViewHolder {
        val itemsView = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_item, parent, false)
        return MyViewHolder(itemsView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = guardianList[position]
        holder.itemView.apply {
            findViewById<TextView>(R.id.contactName).text = currentItem.name
            findViewById<TextView>(R.id.phonePlaceholder).text = currentItem.phone
        }
    }

    override fun getItemCount(): Int {
        return guardianList.size
    }
}