package pl.birskidev.mailattwarda.presentation.ui.new_message

import android.view.View

interface NewMessageListener {

    fun toastMessage(view: View, message: String)
}