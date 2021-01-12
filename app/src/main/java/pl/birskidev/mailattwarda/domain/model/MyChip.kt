package pl.birskidev.mailattwarda.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyChip (
        var displayedNumber: String = "1",
        var firstNumber: Int = 1,
        var lastNumber: Int = 20,
) : Parcelable