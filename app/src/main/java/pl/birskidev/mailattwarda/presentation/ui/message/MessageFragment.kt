package pl.birskidev.mailattwarda.presentation.ui.message

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.databinding.MessageFragmentBinding
import pl.birskidev.mailattwarda.domain.model.MyMessage

private val STORAGE_PERMISSION_CODE: Int = 1000

@AndroidEntryPoint
class MessageFragment : Fragment() {

    private var _binding: MessageFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel : MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.get("myMessage")?.let { message ->
            viewModel.selectMessage(message as MyMessage)
            viewModel.selectContext(activity)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    view?.let { viewModel.downloadMessages(it) }
                } else {
                    Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}