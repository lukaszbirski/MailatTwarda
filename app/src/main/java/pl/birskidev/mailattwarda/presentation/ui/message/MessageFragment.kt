package pl.birskidev.mailattwarda.presentation.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.databinding.MessageFragmentBinding
import pl.birskidev.mailattwarda.presentation.ui.message.adapter.AttachmentAdapter

@AndroidEntryPoint
class MessageFragment : Fragment() {

    private var _binding: MessageFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("shortMessageId").let { id ->
            viewModel.selectMessageId(id!!)
            viewModel.selectContext(activity)
        }
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
                it.adapter = AttachmentAdapter(attachments)
            }
        })
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}