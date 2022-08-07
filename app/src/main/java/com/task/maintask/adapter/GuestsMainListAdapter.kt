package com.task.maintask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.maintask.databinding.ItemGuestReservationsBinding
import com.task.maintask.model.Guest

class GuestsMainListAdapter(
    val itemClick: () -> Unit,
) : RecyclerView.Adapter<GuestsMainListAdapter.MyViewHolder>() {

    var lists: List<Guest> = listOf()


    inner class MyViewHolder(val binding: ItemGuestReservationsBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = ItemGuestReservationsBinding.inflate(
            (LayoutInflater.from(parent.context)), parent, false
        )
        return MyViewHolder(binding)
    }

    fun setItemsList(list: List<Guest>) {
        lists = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = lists.get(position)
        holder.itemView.apply {
            holder.binding.title.text = item.title
            var adapter: GuestsAdapter = GuestsAdapter(itemClick)
            holder.binding.childRecyclerView.adapter = adapter
            adapter.setItemsList(item.guestsList)

        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }
}