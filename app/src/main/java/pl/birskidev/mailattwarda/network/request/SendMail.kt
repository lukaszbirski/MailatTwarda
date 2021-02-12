package pl.birskidev.mailattwarda.network.request

import io.reactivex.Completable

interface SendMail {

    fun sendMail(emailTo: List<String>, subject: String, message: String,login: String, password: String, person: String): Completable
}