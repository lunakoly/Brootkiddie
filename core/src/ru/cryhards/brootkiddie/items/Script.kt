package ru.cryhards.brootkiddie.items

/**
 * Represents a script that can be combined into malware
 */
class Script(name: String, info: String, var size: Float) : Item(name, info, Item.Type.SCRIPT) {

    /**
     * Returns malware that is a combination of the 2 scripts
     */
    fun combine(script: Script) = Malware(
            this.name.substring(0, 3) + " + " + script.name.substring(0, 3),
            "Combo of " + this.name + " and " + script.name, this, script)

    /**
     * Adds this to malware and returns it
     */
    fun combine(malware: Malware): Malware {
        malware.scripts.add(this)
        return malware
    }

    operator fun plus(script: Script) = combine(script)

    operator fun plus(malware: Malware) = combine(malware)


    val dependencies = Array<Dependency?>(4) { _ -> null }

    class Dependency {
        // TODO: Dependency luna_koly
    }

}