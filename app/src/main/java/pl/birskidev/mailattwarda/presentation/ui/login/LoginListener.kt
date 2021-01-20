package pl.birskidev.mailattwarda.presentation.ui.login

import android.view.View

interface LoginListener {

    fun toastMessage(view: View, message: String)
}