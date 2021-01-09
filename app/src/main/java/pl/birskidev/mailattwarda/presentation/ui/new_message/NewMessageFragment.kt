package pl.birskidev.mailattwarda.presentation.ui.new_message

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.FragmentNewMessageBinding

@AndroidEntryPoint
class NewMessageFragment : Fragment() {

    private var _binding: FragmentNewMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewMessageBinding.inflate(inflater, container, false)
        val viewModel : NewMessageViewModel by viewModels()
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}