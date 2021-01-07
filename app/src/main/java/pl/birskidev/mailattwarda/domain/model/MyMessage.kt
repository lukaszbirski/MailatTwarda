package pl.birskidev.mailattwarda.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MyMessage(
        val title: String? = null,
        val content: String? = null,
        val sender: String? = null,
        val recipients: List<String> = listOf(),
        val date: String? = null,
        val time: String? = null,
        val hasAttachments: Boolean? = false
) : Parcelable