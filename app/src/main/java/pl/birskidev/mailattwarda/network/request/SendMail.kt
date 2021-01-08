package pl.birskidev.mailattwarda.network.request

import io.reactivex.Completable

interface SendMail {

    fun sendMail(emailTo: String, subject: String, message: String): Completable
}