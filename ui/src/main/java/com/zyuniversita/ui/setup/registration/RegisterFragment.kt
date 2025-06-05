package com.zyuniversita.ui.setup.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.zyuniversita.domain.model.authentication.RegistrationInfo
import com.zyuniversita.ui.databinding.FragmentRegistrationBinding
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import com.zyuniversita.ui.setup.SetupViewModel
import com.zyuniversita.ui.setup.uistate.AuthEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment() : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()

    private val activityViewModel: SetupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        AuthEvent.AuthenticationSuccessful, AuthEvent.DownloadSuccessful -> setUsernameAndUserID()
                        AuthEvent.ConnectionError -> createToast("Connection error. Try again later.")
                        AuthEvent.IdentificationError -> createToast("Email or password is or are wrong.")
                        AuthEvent.LoginAuth -> activityViewModel.changePage(Page.LOGIN)
                        AuthEvent.RegistrationAuth -> activityViewModel.changePage(Page.REGISTER)
                        AuthEvent.LocalRegistrationAuth -> activityViewModel.changePage(Page.LOCAL_REGISTER)
                    }

                    enableUI(true)
                }
            }
        }

        with(binding) {
            registerButton.setOnClickListener {
                // trim to remove white spaces
                val email = binding.emailEditText.text.toString().trim()
                val username = binding.usernameEditText.text.toString().trim()
                // handle password in other mode
                val password = binding.passwordEditText.text.toString()

                if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {
                    enableUI(false)

                    val registrationInfo =
                        RegistrationInfo(email = email, username = username, password = password)
                    viewModel.register(registrationInfo)
                } else {
                    createToast("Email or password cannot be null")
                }
            }

            loginButton.setOnClickListener {
                viewModel.goToLogin()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun setUsernameAndUserID() {
        viewModel.responseInfo?.let { responseInfo ->
            val userId = responseInfo.userId
            val username = responseInfo.username

            activityViewModel.onUsernameConfirmed(userId, username)
        }
    }

    private fun createToast(text: String) {
        Toast.makeText(
            requireContext(), text, Toast.LENGTH_SHORT
        ).show()
    }

    private fun enableUI(state: Boolean) {
        val refId: IntArray = binding.registrationGroup.referencedIds

        refId.forEach {
            val singleView = binding.root.findViewById<View>(it)

            singleView?.apply {
                isEnabled = state
            }
        }
    }
}