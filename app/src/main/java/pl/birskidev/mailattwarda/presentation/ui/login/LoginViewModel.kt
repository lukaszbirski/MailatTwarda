package pl.birskidev.mailattwarda.presentation.ui.login

import android.util.Log
import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import pl.birskidev.mailattwarda.R
import pl.birskidev.mailattwarda.domain.model.MyChip
import pl.birskidev.mailattwarda.domain.model.MyMessage
import pl.birskidev.mailattwarda.repository.CheckCredentialsRepository
import pl.birskidev.mailattwarda.util.TAG

class LoginViewModel
@ViewModelInject
constructor(
    private val repository: CheckCredentialsRepository
): ViewModel() {

    private val disposable = CompositeDisposable()

    var login: String? = null
    var password: String? = null
    var person: String? = null
    private var callback: LoginFragment = LoginFragment()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        _loading.postValue(false)

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
        Log.d(TAG, "onSuccess: ")
        disposable.add(
            repository.checkCredentialsRepository(login!!, password!!)
                .subscribeOn(Schedulers.computation())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribeWith(object : DisposableSingleObserver<Boolean>() {
                    override fun onSuccess(value: Boolean?) {
                        Log.d(TAG, "onSuccess: $value")
                        _loading.postValue(value)
                    }

                    override fun onError(e: Throwable?) {
                    }

                })
        )

        if (loading.value == false) {
            callback.toastMessage(
                view,
                view.context.resources.getString(R.string.wrong_credentials_string)
            )
            return
        }

        //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_messageListFragment)
    }

}