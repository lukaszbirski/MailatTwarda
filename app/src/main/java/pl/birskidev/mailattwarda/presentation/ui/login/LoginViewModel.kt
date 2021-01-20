package pl.birskidev.mailattwarda.presentation.ui.login

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import pl.birskidev.mailattwarda.R

class LoginViewModel
@ViewModelInject
constructor(): ViewModel() {

    var login: String? = null
    var password: String? = null
    var person: String? = null
    private var callback: LoginFragment = LoginFragment()

    fun logIn(view: View) {
        if (login.isNullOrEmpty() || password.isNullOrEmpty() || person.isNullOrEmpty()) {
            callback.toastMessage(
                view,
                view.context.resources.getString(R.string.credentials_required_string)
            )
            return
        }

        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_messageListFragment)
    }

}