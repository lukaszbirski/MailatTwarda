package pl.birskidev.mailattwarda.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import pl.birskidev.mailattwarda.domain.model.MyMessage

class ShareDataViewModel
@ViewModelInject
constructor(

) : ViewModel() {

    var myMessage: MyMessage? = null
}