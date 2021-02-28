package pl.birskidev.mailattwarda.presentation.ui.new_message

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.FragmentNewMessageBinding
import pl.birskidev.mailattwarda.presentation.ShareDataViewModel

@AndroidEntryPoint
class NewMessageFragment : Fragment(), NewMessageListener {

    private var _binding: FragmentNewMessageBinding? = null
    private val binding get() = _binding!!
    lateinit var dialog: AlertDialog

    private val viewModel : NewMessageViewModel by viewModels()
    private val shareDataViewModel : ShareDataViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shareDataViewModel.myMessage?.let { viewModel.selectMessage(it) }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewMessageBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.toolbar.inflateMenu(R.menu.new_message_fragment_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_add_cc -> {
                    if (binding.ccLinearLayout.visibility == View.GONE) binding.ccLinearLayout.visibility = View.VISIBLE else binding.ccLinearLayout.visibility = View.GONE
                }
            }
            true
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun toastMessage(view: View, message: String) {
        Toast.makeText(view.context, message, Toast.LENGTH_LONG).show()
    }

    override fun setProgressDialog(view: View) {
        val linearLayoutPadding = 30
        val linearLayout = LinearLayout(view.context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        linearLayout.setPadding(linearLayoutPadding, linearLayoutPadding, linearLayoutPadding, linearLayoutPadding)
        linearLayout.gravity = Gravity.CENTER
        var linearLayoutParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        linearLayoutParam.gravity = Gravity.CENTER
        linearLayout.layoutParams = linearLayoutParam
        val progressBar = ProgressBar(view.context)
        progressBar.isIndeterminate = true
        progressBar.setPadding(0, 0, linearLayoutPadding, 0)
        progressBar.layoutParams = linearLayoutParam
        linearLayoutParam = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        linearLayoutParam.gravity = Gravity.CENTER
        val textViewText = TextView(view.context)
        textViewText.text = view.resources.getString(R.string.sending_string)
        textViewText.setTextColor(Color.parseColor("#000000"))
        textViewText.textSize = 20f
        textViewText.layoutParams = linearLayoutParam
        linearLayout.addView(progressBar)
        linearLayout.addView(textViewText)
        val builder: AlertDialog.Builder = AlertDialog.Builder(view.context)
        builder.setCancelable(true)
        builder.setView(linearLayout)
        dialog = builder.create()
        dialog.show()
        val window: Window = dialog.window!!
        if (window != null) {
            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(dialog.window!!.attributes)
            layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            dialog.window!!.attributes = layoutParams
        }
    }

    override fun dismissProgressDialog(view: View) {
        dialog.dismiss()
    }

}