package com.example.criminalintent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.criminalintent.data.Model.Photo
import com.example.criminalintent.databinding.FragmentIntentBinding

class IntentFragment : Fragment(R.layout.fragment_intent) {

    private var _binding: FragmentIntentBinding? = null
    private val adapter = IntentListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIntentBinding.bind(view)
        init()
    }

        fun getAllIntents(): LiveData<List<IntentModel>> {
        return REPOSITORY.allIntents
    }

    private fun init() {
        _binding?.apply {
            rcView2.layoutManager = GridLayoutManager(APP, 3)
            rcView2.adapter = adapter
                getAllIntents().observe(viewLifecycleOwner) { listIntents ->
                    listIntents.asReversed()
                    adapter.setList(listIntents)
                }
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