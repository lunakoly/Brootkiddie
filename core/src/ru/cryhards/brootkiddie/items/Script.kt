package ru.cryhards.brootkiddie.items

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import ru.cryhards.brootkiddie.ui.UI

/**
 * Represents a script that can be combined into malware
 */
class Script(title: String, info: String, iconTexture: Texture, var size: Int) : Item(title, info, iconTexture, Item.Type.SCRIPT), Combinable {

    /**
     * The script level affects effects stats
     * Initial value = 1
     */
    var level = 1

    /**
     * Used by BlockSpace's to determine saved position
     */
    var gridX: Int = 0

    /**
     * Used by BlockSpace's to determine saved position
     */
    var gridY: Int = 0


    /**
     * Returns the result of affecting the empty
     * malware stats. Use it to modulate
     * the target malware by
     */
    fun affection(): Malware.Stats {
        // script effects are applied to virtual stats first

        val affected = Malware.Stats()
        effects.forEach { it.affect(affected, level) }
        temporaryEffects.forEach { it.affect(affected, level) }
        return affected
    }


    /**
     * Use it to add info about sideAffectionAffections
     */
    var sideEffectsDescription = ""

    /**
     * Affects the given script as if it was placed in the given position
     *
     *             TOP
     *           1 2 3 4
     *           _ _ _ _
     *        1|         |1
     * LEFT   2|         |2   RIGHT
     *        3|         |3
     *           - - - -
     *           1 2 3 4
     *           BOTTOM
     */
    var sideAffection: (Script, Script.SIDES, Int) -> Unit = { _, _, _ -> }


    /**
     * Use it to add info about dependencies
     */
    var dependencyDescription = ""

    /**
     * Affects this script as if it was placed near some script
     *
     *             TOP
     *           1 2 3 4
     *           _ _ _ _
     *        1|         |1
     * LEFT   2|         |2   RIGHT
     *        3|         |3
     *           - - - -
     *           1 2 3 4
     *           BOTTOM
     */
    var applyDependency: (Script, Script.SIDES, Int) -> Unit = { _, _, _ -> }


    /**
     * Effects that depend on outer world
     */
    val temporaryEffects = ArrayList<Effect>()

    /**
     * Returns effect with matching title
     */
    override fun findEffect(name: String) = effects.find { it.title == name } ?: temporaryEffects.find { it.title == name }


    /**
     * Returns malware that is a combination of the 2 scripts
     */
    fun combine(script: Script) = Malware(
            this.title.substring(0, 3) + " + " + script.title.substring(0, 3),
            "Combo of " + this.title + " and " + script.title, Texture("img/items/malware.png"), this, script)

    /**
     * Adds this to malware and returns it
     */
    fun combine(malware: Malware): Malware {
        malware.scripts.add(this)
        return malware
    }

    operator fun plus(script: Script) = combine(script)

    operator fun plus(malware: Malware) = combine(malware)


    /**
     * Combines the given item with itself
     */
    override fun combine(item: Item): Item {
        if (item is Script)
            return combine(item)
        if (item is Malware)
            return combine(item)
        return this
    }

    operator fun plus(item: Item) = combine(item)


    /**
     * Used to lay out custom stuff in explorer
     */
    override fun represent(): Array<Actor> {
        val out = ArrayList<Actor>()

        val eff = UI.StaticLabel("Effects:")
        eff.setWrap(true)
        eff.style.background = null
        eff.color = Color.CORAL
        if (effects.size != 0) {
            effects.forEach { eff.setText(eff.text.toString() + "\n * ${it.title}\n ${it.info}") }
        } else {
            eff.setText(eff.text.toString() + "\n ** NONE **")
        }
        out += eff

        val temp = UI.StaticLabel("Affection:")
        temp.setWrap(true)
        temp.style.background = null
        temp.color = Color.RED
        if (temporaryEffects.size != 0) {
            temporaryEffects.forEach { temp.setText(temp.text.toString() + "\n * ${it.title}\n ${it.info}") }
            out += temp
        }

        val side = UI.StaticLabel("Side Affections:\n")
        side.setWrap(true)
        side.style.background = null
        side.color = Color.GREEN
        if (sideEffectsDescription.isNotEmpty()) {
            side.setText(side.text.toString() + sideEffectsDescription)
            out += side
        }

        val dep = UI.StaticLabel("Dependencies:\n")
        dep.setWrap(true)
        dep.style.background = null
        dep.color = Color.BROWN
        if (dependencyDescription.isNotEmpty()) {
            dep.setText(dep.text.toString() + dependencyDescription)
            out += dep
        }

        actions.forEach { out += it }
        return out.toTypedArray()
    }


    /**
     * Used to apply special effects to other scripts
     */
    enum class SIDES(val text: String) {
        TOP("top"),
        LEFT("left"),
        RIGHT("right"),
        BOTTOM("bottom")
    }

    override fun clone(): Any {
        return super<Combinable>.clone()
    }
}