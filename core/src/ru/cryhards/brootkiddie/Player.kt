package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.malware.Malware

open class Player {
    var malwareList: List<Malware> = emptyList()

    var crypto = 0f

    fun addMalware(malware: Malware) {
        malwareList += malware
    }

    fun loadProfile() {}
}