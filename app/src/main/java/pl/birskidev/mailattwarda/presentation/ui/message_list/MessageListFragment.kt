package pl.birskidev.mailattwarda.presentation.ui.message_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.MessageListFragmentBinding
import pl.birskidev.mailattwarda.util.TAG

@AndroidEntryPoint
class MessageListFragment : Fragment() {

    private var _binding: MessageListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MessageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MessageListFragmentBinding.inflate(inflater, container, false)
        viewModel.messages.observe(viewLifecycleOwner, { messages ->
            Log.d(TAG, "onSuccess: ${messages.size}")
        })
        binding.buttonToMessageFragment.setOnClickListener { findNavController().navigate(R.id.action_messageListFragment_to_messageFragment) }
        binding.buttonToNewMessageFragment.setOnClickListener { findNavController().navigate(R.id.action_messageListFragment_to_newMessageFragment) }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}