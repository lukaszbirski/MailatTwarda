package pl.birskidev.mailattwarda.presentation.ui.message_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.MessageListFragmentBinding
import pl.birskidev.mailattwarda.domain.model.MyChip
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.presentation.ShareDataViewModel
import pl.birskidev.mailattwarda.presentation.ui.message_list.adapter.ChipAdapter
import pl.birskidev.mailattwarda.presentation.ui.message_list.adapter.MessageAdapter
import pl.birskidev.mailattwarda.presentation.ui.message_list.adapter.RecyclerViewClickListener

@AndroidEntryPoint
class MessageListFragment : Fragment(), RecyclerViewClickListener, MessageListListener {

    private var _binding: MessageListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MessageListViewModel by viewModels()
    private val shareDataViewModel: ShareDataViewModel by activityViewModels()

    lateinit var dialogBuilder: AlertDialog.Builder
    lateinit var dialog: AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MessageListFragmentBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            shareDataViewModel.myMessage = null
            findNavController().navigate(R.id.action_messageListFragment_to_newMessageFragment)
        }
        viewModel.chips.observe(viewLifecycleOwner, { chips ->
            binding.chipsList.also {
                it.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                it.setHasFixedSize(true)
                it.adapter = ChipAdapter(chips, this)
            }
        })
        viewModel.messages.observe(viewLifecycleOwner, { messages ->
            binding.messagesList.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = MessageAdapter(messages, this)
            }
            binding.messagesList.visibility = View.VISIBLE
        })
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (it) {
                    setProgressDialog()
                    binding.messagesList.visibility = View.GONE
                } else dismissProgressDialog()
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRecyclerViewItemClick(view: View, any: Any) {
        when (any) {
            is MyMessage -> {
                if (!any.equals(null)) {
                    shareDataViewModel.myMessage = any
                    findNavController().navigate(R.id.action_messageListFragment_to_messageFragment)
                }
            }
            is MyChip -> {
                setProgressDialog()
                binding.chipsList.adapter?.notifyDataSetChanged()
                viewModel.loadMessages(any.firstNumber, any.lastNumber)
                binding.messagesList.adapter?.notifyDataSetChanged()
                dismissProgressDialog()
            }
        }
    }

    override fun setProgressDialog() {
        dialogBuilder = AlertDialog.Builder(requireView().context)

        dialogBuilder.setView(layoutInflater.inflate(R.layout.download_message_popup, null))
        dialog = dialogBuilder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
    }

    override fun dismissProgressDialog() {
        dialog.dismiss()
    }
}