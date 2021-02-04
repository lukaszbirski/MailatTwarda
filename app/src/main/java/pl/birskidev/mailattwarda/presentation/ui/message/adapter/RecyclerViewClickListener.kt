package pl.birskidev.mailattwarda.presentation.ui.message.adapter

import android.view.View
import javax.mail.internet.MimeBodyPart

interface RecyclerViewClickListener {

    fun onRecyclerViewItemClick(view: View, attachment: MimeBodyPart)
}