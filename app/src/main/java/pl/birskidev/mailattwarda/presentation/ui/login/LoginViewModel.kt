package pl.birskidev.mailattwarda.presentation.ui.login

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.repository.CheckCredentialsRepository

class LoginViewModel
@ViewModelInject
constructor(
    private val repository: CheckCredentialsRepository
) : ViewModel() {

    var login: String? = null
    var password: String? = null
    var person: String? = null
    private var callback: LoginFragment = LoginFragment()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _credentials = MutableLiveData<Boolean>()
    val credentials: LiveData<Boolean> get() = _credentials

    init {
        _loading.postValue(false)
        _credentials.postValue(null)
    }

    fun logIn(view: View) {
        if (login.isNullOrEmpty() || password.isNullOrEmpty() || person.isNullOrEmpty()) {
            callback.toastMessage(
                view,
                view.context.resources.getString(R.string.credentials_required_string)
            )
            return
        }

        _loading.postValue(true)

        GlobalScope.launch {
            val result = repository.checkCredentialsRepository(login!!, password!!)
            _loading.postValue(false)
            _credentials.postValue(result)
        }
    }

}