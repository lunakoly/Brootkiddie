package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.malware.Malware
import ru.cryhards.brootkiddie.malware.scripts.BaseScript
import ru.cryhards.brootkiddie.malware.scripts.Script


open class Player {
    var malwareList: List<Malware> = emptyList()
    var baseList : List<BaseScript> = emptyList()
    var scriptList : List<Script> = emptyList()

    var crypto = 0f

    fun addMalware(malware: Malware) {
        malwareList += malware
    }

    fun loadProfile() {}
}