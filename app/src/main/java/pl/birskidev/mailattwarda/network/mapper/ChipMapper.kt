package pl.birskidev.mailattwarda.network.mapper

import pl.birskidev.mailattwarda.domain.model.MyChip
import pl.birskidev.mailattwarda.domain.util.DomainMapper

class ChipMapper : DomainMapper<Int, List<MyChip>> {

    override fun mapToDomainModel(entity: Int): List<MyChip> {
        var chips: MutableList<MyChip> = mutableListOf()
        var firstNumbersList: MutableList<Int> = mutableListOf()
        var lastNumbersList: MutableList<Int> = mutableListOf()

        for (i in 1..entity) {
            if (i%15 == 1) {
                firstNumbersList.add(i)
            }
            if (i%15 == 0 || i == entity) {
                lastNumbersList.add(i)
            }
        }

        for (i in 1..firstNumbersList.size) {
            var myChip = MyChip()
            myChip.firstNumber = firstNumbersList[i-1]
            myChip.lastNumber = lastNumbersList[i-1]
            myChip.displayedNumber = i.toString()
            chips.add(myChip)
        }

        return chips.toList()
    }
}