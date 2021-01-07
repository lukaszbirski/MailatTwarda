package pl.birskidev.mailattwarda.presentation.ui.message_list.adapter

import android.view.View
import pl.birskidev.mailattwarda.domain.model.MyMessage

interface RecyclerViewClickListener {

    fun onRecyclerViewItemClick(view: View, myMessage: MyMessage)
}