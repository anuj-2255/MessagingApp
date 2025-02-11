package com.example.messagingapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.messagingapp.R
import com.example.messagingapp.model.SmsData
import com.example.messagingapp.databinding.ItemSmsBinding

class SmsAdapter() :
    RecyclerView.Adapter<SmsAdapter.SmsViewHolder>() {
    private val smsList = arrayListOf<SmsData>()
    class SmsViewHolder(private val binding: ItemSmsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item:SmsData){
          binding.apply {
              senderText.text = item.sender ?: binding.root.context.getString(R.string.no_sendernumber_found)
              bodyText.text = item.body ?: binding.root.context.getString(R.string.no_body_found)
              timeText.text = item.formattedDate().ifEmpty { binding.root.context.getString(R.string.no_date_found) }
          }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmsViewHolder {
        val view = ItemSmsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SmsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SmsViewHolder, position: Int) {
        holder.bind(smsList[position])
    }

    override fun getItemCount() = smsList.size


    fun setData(list:List<SmsData>){
        smsList.clear()
        smsList.addAll(list)
        notifyDataSetChanged()
    }

}