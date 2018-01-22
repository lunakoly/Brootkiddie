package ru.cryhards.brootkiddie

import ru.cryhards.brootkiddie.malware.Distribution
import ru.cryhards.brootkiddie.malware.Malware

open class Player {
    var malwareList: List<Malware> = emptyList()

    val totalNodes = Int.MAX_VALUE
    var infectedNodes = 1
    var crypto = 0f
    var days = 0
    lateinit var distribution : Distribution

    fun addMalware(malware: Malware) {
        malwareList += malware
    }

    fun loadProfile() {}

    fun doDay() {
        infectedNodes = 0
        for (m in malwareList) {
            m.recalcStats()
            infectedNodes += m.infected
            crypto += m.minedcrypto
        }
    }
}