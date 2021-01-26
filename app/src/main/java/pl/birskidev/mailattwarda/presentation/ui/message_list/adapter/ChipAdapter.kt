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

    private var checked = 0

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
        holder.bind(chips[position], position)
        holder.recyclerViewMyChipBinding.root.setOnClickListener {
            listener.onRecyclerViewItemClick(holder.recyclerViewMyChipBinding.root, chips[position])
            holder.check(position)
        }
    }

    override fun getItemCount() = chips.size

    inner class ChipViewHolder(
            val recyclerViewMyChipBinding: ItemChipBinding
    ) : RecyclerView.ViewHolder(recyclerViewMyChipBinding.root) {

        fun check(position: Int) {
            checked = position
        }

        fun bind(myChip: MyChip, position: Int) {
            if (position == checked) myChip.isChecked = true
            if (position != checked) myChip.isChecked = false
        }
    }

}