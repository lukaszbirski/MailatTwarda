package pl.birskidev.mailattwarda.presentation.ui.message_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.MessageListFragmentBinding
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.presentation.ui.message_list.adapter.MessageAdapter
import pl.birskidev.mailattwarda.presentation.ui.message_list.adapter.RecyclerViewClickListener

@AndroidEntryPoint
class MessageListFragment : Fragment(), RecyclerViewClickListener {

    private var _binding: MessageListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MessageListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MessageListFragmentBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_messageListFragment_to_newMessageFragment ) }
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.loadingView.visibility = View.VISIBLE
            viewModel.fetchMails()
            binding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.messages.observe(viewLifecycleOwner, { messages ->
            binding.messagesList.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = MessageAdapter(messages, this)
            }
            binding.messagesList.visibility = View.VISIBLE
        })
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let { binding.loadingView.visibility = if (it) View.VISIBLE else View.GONE
            if (it) {binding.messagesList.visibility = View.GONE}
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRecyclerViewItemClick(view: View, myMessage: MyMessage) {
        if (!myMessage.equals(null)) {
            val bundle = bundleOf("myMessage" to myMessage)
            findNavController().navigate(R.id.action_messageListFragment_to_messageFragment, bundle)
        }
    }
}