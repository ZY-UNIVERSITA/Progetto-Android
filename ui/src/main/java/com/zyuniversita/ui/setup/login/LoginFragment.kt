package com.zyuniversita.ui.setup.login

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
import com.zyuniversita.domain.model.authentication.LoginInfo
import com.zyuniversita.ui.databinding.FragmentLoginBinding
import com.zyuniversita.ui.main.mainactivity.mainenum.Page
import com.zyuniversita.ui.setup.SetupViewModel
import com.zyuniversita.ui.setup.uistate.AuthEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment() : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    private val activityViewModel: SetupViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event.collect { event ->
                    when (event) {
                        AuthEvent.DownloadSuccessful -> setUsernameAndUserID()
                        AuthEvent.AuthenticationSuccessful -> createToast("Wait...")
                        AuthEvent.ConnectionError -> createToast("Connection error. Try again later.")
                        AuthEvent.IdentificationError -> createToast("Email or password is or are wrong.")
                        AuthEvent.LoginAuth -> activityViewModel.changePage(Page.LOGIN)
                        AuthEvent.RegistrationAuth -> activityViewModel.changePage(Page.REGISTER)
                        AuthEvent.LocalRegistrationAuth -> activityViewModel.changePage(Page.LOCAL_REGISTER)
                    }
                }
            }
        }

        with(binding) {
            loginButton.setOnClickListener {
                // trim to remove white spaces
                val email = binding.emailEditText.text.toString().trim()
                val password = binding.passwordEditText.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    val loginInfo =
                        LoginInfo(email = email, password = password)

                    viewModel.login(loginInfo)
                } else {
                    createToast("Email or password cannot be null")
                }
            }

            registerButton.setOnClickListener {
                viewModel.goToRegistrationPage()
            }

            localRegistrationButton.setOnClickListener {
                viewModel.goToLocalRegistrationPage()
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
}