package com.example.criminalintent

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.criminalintent.databinding.ListItemModelBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class IntentListAdapter: RecyclerView.Adapter<IntentListAdapter.IntentHolder>() {
    var listPhotos = emptyList<IntentModel>()

    class IntentHolder(item: View):RecyclerView.ViewHolder(item)  {
        val binding = ListItemModelBinding.bind(item)
        fun bind(photo: IntentModel) = with(binding) {
            itemView.setOnClickListener {
                addIntent(IntentModel(
                    url_s = photo.url_s, farm = photo.farm, height_s = photo.height_s,
                    isfamily = photo.isfamily, isfriend = photo.isfriend, ispublic = photo.ispublic,
                    owner = photo.owner, secret = photo.secret, server = photo.server, width_s = photo.width_s, title = photo.title)){}
                Toast.makeText(APP, "Image saved", Toast.LENGTH_SHORT).show()
            }
            Glide.with(im.rootView.context).asBitmap().load(photo.url_s).override( 150, 150 ).thumbnail( 0.5f ).diskCacheStrategy(
                DiskCacheStrategy.NONE).dontTransform().centerCrop().placeholder(R.drawable.sample_bitmap).error(R.drawable.black_box).into(im)
        }


        @SuppressLint("SetTextI18n")
        @OptIn(DelicateCoroutinesApi::class)
        private fun addIntent(intent: IntentModel, onSuccess:() -> Unit ) {
            GlobalScope.launch {
                REPOSITORY.insertIntent(intent) {
                    onSuccess()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_model, parent, false)
        return IntentHolder(view)
    }

    override fun onBindViewHolder(holder: IntentHolder, position: Int) {
        holder.bind(listPhotos[position])
    }


    override fun getItemCount(): Int {
        return listPhotos.size
    }

    fun setList(intentsList: List<IntentModel>) {
        listPhotos = intentsList
        notifyDataSetChanged()
    }

}