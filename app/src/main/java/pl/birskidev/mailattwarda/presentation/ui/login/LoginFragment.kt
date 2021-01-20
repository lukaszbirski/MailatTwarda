package pl.birskidev.mailattwarda.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.LoginFragmentBinding

@AndroidEntryPoint
class LoginFragment : Fragment(), LoginListener {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let { binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE }
        })
        viewModel.credentials.observe(viewLifecycleOwner, {credentials ->
            if (credentials == true) {
                findNavController().navigate(R.id.action_loginFragment_to_messageListFragment)
            } else if (credentials == false) {
                Toast.makeText(context,
                    context?.resources?.getString(R.string.wrong_credentials_string),
                    Toast.LENGTH_LONG
                ).show()
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun toastMessage(view: View, message: String) {
        Toast.makeText(view.context, message, Toast.LENGTH_LONG).show()
    }
}