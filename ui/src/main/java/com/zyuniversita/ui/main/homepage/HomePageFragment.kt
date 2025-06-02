package com.zyuniversita.ui.main.homepage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zyuniversita.ui.databinding.FragmentHomepageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageFragment: Fragment() {
    private var _binding: FragmentHomepageBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = "Homepage Main fragment TAG"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }


}