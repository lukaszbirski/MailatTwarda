package pl.birskidev.mailattwarda.presentation.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.databinding.MessageFragmentBinding
import pl.birskidev.mailattwarda.domain.model.MyMessage

@AndroidEntryPoint
class MessageFragment : Fragment() {

    private var _binding: MessageFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.get("myMessage")?.let { message ->
            viewModel.selectMessage(message as MyMessage)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MessageFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}