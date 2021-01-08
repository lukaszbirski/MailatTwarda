package pl.birskidev.mailattwarda.network.request

import io.reactivex.Completable

interface SendMail {

    fun sendMail(email: String, subject: String, message: String): Completable
}