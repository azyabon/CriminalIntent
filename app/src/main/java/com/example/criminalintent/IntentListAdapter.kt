package com.example.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.ListItemModelBinding

class IntentListAdapter: RecyclerView.Adapter<IntentListAdapter.IntentHolder>() {
    val intentList = ArrayList<IntentModel>()

    class IntentHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = ListItemModelBinding.bind(item)
        fun bind(intent: IntentModel) = with(binding) {
            im.setImageResource(intent.imageId)
            tvTitle.text = intent.title
            tvDate.text = intent.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_model, parent, false)
        return IntentHolder(view)
    }

    override fun onBindViewHolder(holder: IntentHolder, position: Int) {
        holder.bind(intentList[position])
    }

    override fun getItemCount(): Int {
        return intentList.size
    }

    fun addIntent(intent: IntentModel) {
        intentList.add(intent)
        notifyDataSetChanged()
    }

}