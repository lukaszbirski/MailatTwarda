package pl.birskidev.mailattwarda.presentation.ui.message.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.ItemAttachmentBinding
import pl.birskidev.mailattwarda.domain.model.Attachment

class AttachmentAdapter(
    private val attachments: List<Attachment>,
    private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<AttachmentAdapter.AttachmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AttachmentViewHolder(
            DataBindingUtil.inflate<ItemAttachmentBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_attachment,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: AttachmentViewHolder, position: Int) {
        holder.recyclerViewAttachmentBinding.attachment = attachments[position]
        holder.recyclerViewAttachmentBinding.root.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerViewAttachmentBinding.root, attachments[position].attachment!!)
        }
    }

    override fun getItemCount() = attachments.size

    inner class AttachmentViewHolder(
        val recyclerViewAttachmentBinding: ItemAttachmentBinding
    ) : RecyclerView.ViewHolder(recyclerViewAttachmentBinding.root)
}