package com.zyuniversita.ui.findcharacter

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.ui.databinding.FragmentFindCharacterBinding
import com.zyuniversita.ui.main.mainactivity.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class FindCharacterFragment() : Fragment() {
    private var _binding: FragmentFindCharacterBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = "Find character fragment TAG"

    private val viewModel: FindCharacterViewModel by viewModels()

    private val activityViewModel: MainActivityViewModel by activityViewModels<MainActivityViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        Log.d(TAG, "Fragment created")

        _binding = FragmentFindCharacterBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.undo.setOnClickListener {
            binding.drawingView.undoStroke()
        }

        binding.clear.setOnClickListener {
            binding.drawingView.clearAllStrokes()
        }

        binding.selectFile.setOnClickListener {
            showImageChoosingDialog()
        }

        binding.send.setOnClickListener {
            uploadImage()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageRecognized.collect { result ->
                    binding.result.text = result
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Fragment destroyed")

        _binding = null
    }

    private fun showImageChoosingDialog() {
        val options: Array<String> = arrayOf("camera", "gallery")
        AlertDialog.Builder(requireContext())
            .setTitle("Scegli sorgente immagine")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> onCameraClick()
                    1 -> onGalleryClick()
                }
                dialog.dismiss()
            }
            .show()
    }

    /*  BUTTON: Choose photo from gallery  */
    private fun onGalleryClick() {
        val request = PickVisualMediaRequest(
            ActivityResultContracts.PickVisualMedia.ImageOnly
        )
        pickImageLauncher.launch(request)
    }

    /*  BUTTON: Take a photo  */
    private fun onCameraClick() {
        val uri = createTempImageUri()     
        viewModel.setImageUri(uri)
        takePictureLauncher.launch(uri)
    }


    /* 1a. Photo Picker: permette di secegliere una foto dalla galleyer oppure scattare una foto usando la funzione fotocmaera della galleria */
    // retituisce l'URI dell'iommagine da usare in modo temporaneao
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                viewModel.setImageUri(uri)
                handleImage(it)
            }          // immagine scelta
                ?: showPermissionDeniedMessage("Gallery")
        }

    /* 1b. TakePicture: permette di scattare una foto usando un app fotocamera di sistema */
    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) viewModel.tempImageUri?.let { handleImage(it) }
                ?: showPermissionDeniedMessage("Camera")
        }


    /*  Crea un URI temporaneo in cache interna delll'app dove salvare temporaneamemte la foto scatta  */
    private fun createTempImageUri(): Uri {
        val cacheDir = requireContext().cacheDir
        val tempFile = File.createTempFile(
            "captured_", ".jpg", cacheDir
        ).apply { deleteOnExit() }

        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            tempFile
        )
    }


    private fun showPermissionDeniedMessage(permission: String) {
        Toast.makeText(
            requireContext(),
            "$permission denied. You cannot use this function.",
            Toast.LENGTH_LONG
        ).show()
    }

    /* Image rendering */
    private fun handleImage(uri: Uri) {
        binding.reset.setOnClickListener(null)

        // Esempio: la metti in un ImageView
        binding.imageView.setImageURI(uri)

        binding.drawGroup.visibility = View.GONE
        binding.imageView.visibility = View.VISIBLE
        binding.reset.visibility = View.VISIBLE


        binding.reset.setOnClickListener {
            binding.imageView.setImageDrawable(null)         
            binding.imageView.visibility = View.GONE
            binding.reset.visibility = View.GONE
            binding.drawGroup.visibility = View.VISIBLE   
        }

    }

    private fun uploadImage() {
        viewModel.uploadImage()
    }
}