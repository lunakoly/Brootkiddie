package ru.cryhards.brootkiddie.levels

import com.badlogic.gdx.Gdx
import ru.cryhards.brootkiddie.Player
import ru.cryhards.brootkiddie.malware.Malware
import ru.cryhards.brootkiddie.malware.MalwareExample
import java.lang.Math.max

open class GlobalMapLevel(player: Player) : Level(player) {
    var days = 0
    var realSecondsPerDay = 0.5f
    var currentDayTime = 0f

    val totalNodes = Int.MAX_VALUE
    var infectedNodes = 1

    var infected = mutableMapOf<Malware, Int>()
    var awareLevel = mutableMapOf<Malware, Float>()
    val criticalAwareLevel = 1f
    var spotted = mutableSetOf<Malware>()

    init {
        val malware = MalwareExample(player) // TODO
        player.addMalware(malware)

        addMalware(malware)
    }

    fun addMalware(malware: Malware) {
        malwareList += malware
        awareLevel[malware] = 0f
        infected[malware] = 1
    }

    override fun act(deltaT: Float) {
        currentDayTime += deltaT
        while (currentDayTime > realSecondsPerDay) {
            currentDayTime -= realSecondsPerDay

            doDay()
            days++
        }
    }

    fun doDay() {
        infectedNodes = 0
        for (m in malwareList) {
            if (!spotted.contains(m)) awareLevel[m] = awareLevel[m]!! + m.irriration / 100f

            Gdx.app.log("Aware", awareLevel.toString())

            if (awareLevel[m]!! >= criticalAwareLevel && infected[m]!! > 0) {
                spotted.add(m)
                infected[m] = infected[m]!! - maxOf((0.15 * infected[m]!!).toInt(), 1)
            }

            val max = getLimit(m.infectiousness)
            var delta = minOf((max * m.speed * (1f + infected[m]!! / max) / 100000f).toInt(), max - infected[m]!!)
            if (spotted.contains(m)) delta = (delta * 0.001f).toInt()
            if (infected[m]!! > 0 && infected[m]!! != max) {
                infected[m] = infected[m]!! + delta
            }

            player.crypto += m.minedcrypto * infected[m]!!

            infectedNodes = max(infectedNodes, infected[m]!!)
        }
    }

    fun getLimit(infectiousness: Float): Int {
        return maxOf(0, ((totalNodes - days * 3) * infectiousness / 10000f).toInt())
    }
}