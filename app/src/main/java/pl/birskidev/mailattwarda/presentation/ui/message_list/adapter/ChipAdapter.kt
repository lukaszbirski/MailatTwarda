package pl.birskidev.mailattwarda.presentation.ui.message_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.databinding.ItemChipBinding
import pl.birskidev.mailattwarda.domain.model.MyChip

class ChipAdapter(
        private val chips: List<MyChip>,
        private val listener: RecyclerViewClickListener
) : RecyclerView.Adapter<ChipAdapter.ChipViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ChipViewHolder(
                    DataBindingUtil.inflate<ItemChipBinding>(
                            LayoutInflater.from(parent.context),
                            R.layout.item_chip,
                            parent,
                            false
                    )
            )

    override fun onBindViewHolder(holder: ChipViewHolder, position: Int) {
        holder.recyclerViewMyChipBinding.chip = chips[position]
        holder.recyclerViewMyChipBinding.root.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerViewMyChipBinding.root, chips[position])
        }
    }

    override fun getItemCount() = chips.size

    inner class ChipViewHolder(
            val recyclerViewMyChipBinding: ItemChipBinding
    ) : RecyclerView.ViewHolder(recyclerViewMyChipBinding.root) {

    }

}