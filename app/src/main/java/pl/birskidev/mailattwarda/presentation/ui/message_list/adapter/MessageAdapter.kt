package pl.birskidev.mailattwarda.presentation.ui.message_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.ItemMessageBinding
import pl.birskidev.mailattwarda.domain.model.ShortMessage

class MessageAdapter(
        private val messages: List<ShortMessage>,
        private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            MessageViewHolder(
                    DataBindingUtil.inflate<ItemMessageBinding>(
                            LayoutInflater.from(parent.context),
                            R.layout.item_message,
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.recyclerViewMyMessageBinding.message = messages[position]
        holder.recyclerViewMyMessageBinding.root.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerViewMyMessageBinding.root, messages[position])
        }
    }

    override fun getItemCount() = messages.size


    inner class MessageViewHolder(
            val recyclerViewMyMessageBinding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(recyclerViewMyMessageBinding.root)


}