package ru.cryhards.brootkiddie.levels

import com.badlogic.gdx.Gdx
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.malware.Malware
import ru.cryhards.brootkiddie.malware.MalwareExample
import ru.cryhards.brootkiddie.malware.scripts.ExampleBaseScript
import ru.cryhards.brootkiddie.malware.scripts.SpreadingScript
import java.lang.Math.max

open class GlobalMapLevel(player: Player) : Level(player) {
    var days = 0
    var realSecondsPerDay = 0.75f
    var currentDayTime = 0f

    // val totalNodes : Long = Int.MAX_VALUE.toLong()
    val totalNodes: Long = 1.3e9.toLong()
    var totalInfectedNodes: Long = 1

    var infectedByMalware = mutableMapOf<Malware, Long>()
    var awareLevelByMalware = mutableMapOf<Malware, Float>()
    var isSpottedByMalware = mutableSetOf<Malware>()
    val criticalAwareLevel = 1f

    init {
        val malware = MalwareExample(player) // TODO
        player.addMalware(malware)

        player.baseList += ExampleBaseScript(1, "Exampler")
        player.baseList += ExampleBaseScript(2, "SUPERExampler")
        player.baseList += ExampleBaseScript(3, "SUPERMEGAExampler")
        player.baseList += ExampleBaseScript(3, "SUPERMEGADUPERExampler")
        player.scriptList += SpreadingScript(1, "Spreader")
        player.scriptList += SpreadingScript(2, "Spreader EVOLVED")
        player.scriptList += SpreadingScript(3, "Infinity and beyond")
        player.scriptList+= SpreadingScript(4, "Can't stop this")

        addMalware(malware)
    }

    fun addMalware(malware: Malware) {
        malwareList += malware
        awareLevelByMalware[malware] = 0f
        infectedByMalware[malware] = 1
    }

    override fun act(deltaT: Float) {
        currentDayTime += deltaT
        while (currentDayTime > realSecondsPerDay) {
            currentDayTime -= realSecondsPerDay

            doDay()
        }
    }

    fun doDay() {
        days++
        Gdx.app.log("DAY", "day: ${days} totalInfected: ${totalInfectedNodes}")

        totalInfectedNodes = 0
        for (m in malwareList) {
            awareLevelByMalware[m] = awareLevelByMalware[m]!! + deltaAware(m)
            infectedByMalware[m] = infectedByMalware[m]!! + deltaInfected(m)

            if (awareLevelByMalware[m]!! > criticalAwareLevel) {
                isSpottedByMalware.add(m)
            }

            player.crypto += m.cryptoMiningSpeed * infectedByMalware[m]!!
            totalInfectedNodes = max(totalInfectedNodes, infectedByMalware[m]!!)
            Gdx.app.log("MALWARE", "name: ${m.name} awareLevel: ${awareLevelByMalware[m]!!} infected: ${infectedByMalware[m]!!}")
        }
    }

    fun deltaAware(m: Malware): Float { // Ln( infected ) * irritation
        return sigmoid(infectedByMalware[m]!!.toFloat() / totalNodes + if (isSpotted(m)) 0.1f else 0f) * m.irritation * (1f / 8f)
    }

    fun deltaInfected(m: Malware): Long {
        val aware = awareLevelByMalware[m]!! / criticalAwareLevel

        val shouldBeInfected = (totalNodes * sigmoid(1.0001f - aware)).toLong()

        var delta = shouldBeInfected - infectedByMalware[m]!!
        delta = (delta * m.speed).toLong()

        if (delta > infectedByMalware[m]!!)
            delta = max(1, (infectedByMalware[m]!! * 0.25f).toLong())

        return delta
    }

    fun sigmoid(x: Float): Float {
        return 1f / (1f + Math.exp(-10.0 * x + 5.0)).toFloat()
    }

    fun isSpotted(m: Malware): Boolean {
        return isSpottedByMalware.contains(m)
    }
}