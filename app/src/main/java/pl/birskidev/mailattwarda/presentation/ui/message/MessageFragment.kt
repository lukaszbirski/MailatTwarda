package pl.birskidev.mailattwarda.presentation.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.databinding.MessageFragmentBinding
import pl.birskidev.mailattwarda.presentation.ShareDataViewModel
import pl.birskidev.mailattwarda.presentation.ui.message.adapter.AttachmentAdapter
import pl.birskidev.mailattwarda.presentation.ui.message.adapter.RecyclerViewClickListener
import javax.mail.internet.MimeBodyPart

@AndroidEntryPoint
class MessageFragment : Fragment(), RecyclerViewClickListener {

    private var _binding: MessageFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MessageViewModel by viewModels()
    private val shareDataViewModel : ShareDataViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.selectContext(activity)
        shareDataViewModel.myMessage?.let { viewModel.selectMessage(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MessageFragmentBinding.inflate(inflater, container, false)
        viewModel.selectedMessage.observe(viewLifecycleOwner, {
            binding.viewmodel = viewModel
        })
        viewModel.attachments.observe(viewLifecycleOwner, { attachments ->
            binding.attachmentList.also {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.setHasFixedSize(true)
                it.adapter = AttachmentAdapter(attachments, this)
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onRecyclerViewItemClick(view: View, attachment: MimeBodyPart) {
        viewModel.downloadMessages(attachment)
    }

}