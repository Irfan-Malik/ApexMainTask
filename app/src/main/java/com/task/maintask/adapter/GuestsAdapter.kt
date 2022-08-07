package com.task.maintask.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.maintask.databinding.ItemGuestsBinding
import com.task.maintask.model.Guests

class GuestsAdapter(val itemClick: () -> Unit) :
    RecyclerView.Adapter<GuestsAdapter.MyViewHolder>() {

    var lists: List<Guests> = listOf()

    inner class MyViewHolder(val binding: ItemGuestsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.guestCheck.setOnCheckedChangeListener { buttonView, isChecked ->
                lists.get(adapterPosition).selected = isChecked
                itemClick.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = ItemGuestsBinding.inflate(
            (LayoutInflater.from(parent.context)), parent, false
        )
        return MyViewHolder(binding)
    }

    fun setItemsList(list: List<Guests>) {
        lists = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.apply {
            holder.binding.guestCheck.text = lists.get(position).title
            holder.binding.guestCheck.isChecked = lists.get(position).selected
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }
}