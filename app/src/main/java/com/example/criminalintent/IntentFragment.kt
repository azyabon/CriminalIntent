package com.example.criminalintent

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.room.TypeConverter
import com.example.criminalintent.databinding.FragmentIntentBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class IntentFragment : Fragment(R.layout.fragment_intent) {

    private var _binding: FragmentIntentBinding? = null

    private lateinit var img: Bitmap;
    private lateinit var imgView: ImageView;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIntentBinding.bind(view)

        val photoBtn = view.findViewById(R.id.photoBtn) as Button
        val sendBtn = view.findViewById(R.id.sendBtn) as Button
        imgView = view.findViewById(R.id.imgView) as ImageView

        photoBtn.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
        }

        @TypeConverter
        fun fromBitmap(bitmap: Bitmap): ByteArray {
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            return outputStream.toByteArray()
        }

        sendBtn.setOnClickListener {
            addIntent(IntentModel(imageId = fromBitmap(img))){}
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            var bmp = data?.extras?.get("data") as Bitmap
            img = bmp
            imgView.setImageBitmap(bmp)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_intent, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = IntentFragment()
    }
}