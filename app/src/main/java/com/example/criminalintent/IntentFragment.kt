package com.example.criminalintent

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.criminalintent.databinding.FragmentIntentBinding
import com.google.android.material.textfield.TextInputEditText
import java.util.*

class IntentFragment : Fragment(R.layout.fragment_intent) {

    private val calendar = Calendar.getInstance()
    private var _binding: FragmentIntentBinding? = null
    private val binding get() = _binding!!

    private var tvDate: String = calendar.get(Calendar.DAY_OF_MONTH).toString() +
            "-" + calendar.get(Calendar.MONTH).toString() +
            "-" + calendar.get(Calendar.YEAR).toString()
    private var isSolved: Boolean = false
    private lateinit var img: Bitmap;
    private lateinit var imgView: ImageView;
    private var text: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIntentBinding.bind(view)


        val dateInput = view.findViewById(R.id.dateInput) as TextView
        val solvCheck = view.findViewById(R.id.solvCheck) as CheckBox
        val photoBtn = view.findViewById(R.id.photoBtn) as Button
        val titleInput = view.findViewById(R.id.titleInput) as TextInputEditText
        imgView = view.findViewById(R.id.imgView) as ImageView

        dateInput.text = tvDate.toString()
        solvCheck.isChecked = isSolved

        photoBtn.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 123)
        }

        solvCheck.setOnClickListener {
            isSolved = !isSolved
        }

        titleInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text = titleInput.text.toString()
                println(text)
            }
        })

        binding.apply {
            dateInput.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager
                datePickerFragment.show(parentFragmentManager, "")
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                    ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        tvDate = date.toString()
                        dateInput.text = date
                    }
                }
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