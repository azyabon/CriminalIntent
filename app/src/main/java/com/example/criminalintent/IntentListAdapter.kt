package com.example.criminalintent

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.ListItemModelBinding

class IntentListAdapter: RecyclerView.Adapter<IntentListAdapter.IntentHolder>() {
    var intentList = emptyList<IntentModel>()

    class IntentHolder(item: View):RecyclerView.ViewHolder(item) {
        val binding = ListItemModelBinding.bind(item)
        fun bind(intent: IntentModel) = with(binding) {

            fun toBitmap(byteArray: ByteArray): Bitmap {
                return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            }

            im.setImageBitmap(toBitmap(intent.imageId))
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

    fun setList(intentsList: List<IntentModel>) {
        intentList = intentsList
        notifyDataSetChanged()
    }

}