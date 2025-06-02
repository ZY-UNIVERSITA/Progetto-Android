package com.zyuniversita.ui.main.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.zyuniversita.ui.databinding.FragmentNavigationBinding
import com.zyuniversita.ui.main.mainactivity.MainActivityViewModel
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NavigationFragment : Fragment() {

    // view binding prima messo a null
    private var _binding: FragmentNavigationBinding? = null

    // getter del valore di binding che garantisce l'uso di _binding se non è null
    private val binding get() = _binding!!

    private val viewModel: MainActivityViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // passa:
        // inflater per creare la view a partire da xml
        // container per passare la viewGroup che conterrà il fragment
        // false per dire che la view non deve essere passato alla viewGroup subito
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)

        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.HomeButton.setOnClickListener {
            this.changeFragment(Page.HOME)
        }

        binding.GameButton.setOnClickListener {
            this.changeFragment(Page.GAME)
        }

        binding.WordButton.setOnClickListener {
            this.changeFragment(Page.THEORY)
        }

        binding.ProfileButton.setOnClickListener {
            this.changeFragment(Page.PROFILE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun changeFragment(fragment: Page) {
        lifecycleScope.launch {
            viewModel.changeFragment(fragment)
        }
    }
}