package ru.cryhards.brootkiddie.items

import com.badlogic.gdx.Gdx
import ru.cryhards.brootkiddie.items.effects.MiningEffect

/**
 * Scripts factory
 */
object Scripts {
    val scripts = mapOf<String, (level: Int) -> Script>(
            Pair("emptyItem", ::emptyItem),
            Pair("loremItem", ::loremItem),
            Pair("spreaderV3000", ::spreaderV3000),
            Pair("spreadingMultiplierLeft", ::spreadingMultiplierLeft),
            Pair("spreadingMultiplierTop", ::spreadingMultiplierTop),
            Pair("spreadingMultiplierRight", ::spreadingMultiplierRight),
            Pair("spreadingMultiplierBottom", ::spreadingMultiplierBottom))

    /**
     * Returns Item debug example
     */
    fun emptyItem(level: Int = 1) = Script(
            "<Name>",
            "<Some info about item>",
            "img/ui/empty.png", 1, level)


    /**
     * Returns Item debug example 2
     */
    fun loremItem(level: Int = 1): Script {

        val block = Script(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum at metus at dapibus.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus fermentum at metus at dapibus. Morbi consequat in eros nec rutrum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Morbi porttitor, metus eget luctus pretium, ligula est sollicitudin risus, id accumsan justo enim eu ante. Aliquam sit amet magna lacus. In commodo rhoncus quam quis faucibus. Sed et odio sit amet tellus consequat egestas id vitae diam. Cras in risus velit. Vestibulum eget tincidunt eros. Integer congue massa vitae nibh interdum, a suscipit eros iaculis. Nullam facilisis consectetur lectus, id venenatis turpis mollis ac. Suspendisse eleifend nunc rutrum sem scelerisque accumsan. Mauris nec vestibulum mi.",
                "img/ui/back.png", 1, level)


        return block
    }


    /**
     * Basic spreading script
     */
    fun spreaderV3000(level: Int = 1): Script {
        val block = Script(
                "Spreader V3000",
                "adds 50 to spreading",
                "img/items/worm.png", 1, level)

        block.applyDependency = { script, sides, i ->
            Gdx.app.log("UI", "DABDM CALLED")

            when (sides) {
                Script.SIDES.BOTTOM -> {
                    if (script.title == "<Name>") {
                        Gdx.app.log("UI", "DABDM")
                        script.temporaryEffects.add(MiningEffect())
                    }
                }
                else -> {
                    // nothing
                }
            }
        }

        block.dependencyDescription = "Adds extra Mining Effect if located to the top of <Name> test item"
        return block
    }


    /**
     * Spreading Multiplier
     */
    fun spreadingMultiplier(level: Int = 1, side: Script.SIDES): Script {

        var path = ""
        when (side) {
            Script.SIDES.TOP -> path = "img/items/up_black.png"
            Script.SIDES.LEFT -> path = "img/items/left_black.png"
            Script.SIDES.RIGHT -> path = "img/items/right_black.png"
            Script.SIDES.BOTTOM -> path = "img/items/down_black.png"
        }

        val block = Script(
                "Spreading Mult",
                "Multiplies spreading effect of the item to the ${side.text} of script",
                path, 1, level)


        block.sideAffection = { script, sides, i ->
            Gdx.app.log("UI", "YAHOOO CALLED")

            when (sides) {
                side -> {
                    val affection = object : Item.Effect(
                            "Affected Spreading (Spreading Mult)",
                            "x2 | Add-on given by Spreading Mult") {
                        override fun affect(target: Any?, vararg dependencies: Any?): Item.Effect {
                            (target as Malware.Stats).spreadingSpeed *= 2
                            return super.affect(target, *dependencies)
                        }
                    }

                    Gdx.app.log("UI", "YAHOOO")

                    script.temporaryEffects.add(affection)
                }
                else -> {
                    // nothing
                }
            }
        }

        block.sideEffectsDescription = "Affects item located to the $side of it"
        block.dependencyDescription = "Np dep provided"
        return block
    }

    fun spreadingMultiplierLeft(level: Int = 1) = spreadingMultiplier(level, Script.SIDES.LEFT)
    fun spreadingMultiplierRight(level: Int = 1) = spreadingMultiplier(level, Script.SIDES.RIGHT)
    fun spreadingMultiplierTop(level: Int = 1) = spreadingMultiplier(level, Script.SIDES.TOP)
    fun spreadingMultiplierBottom(level: Int = 1) = spreadingMultiplier(level, Script.SIDES.BOTTOM)
}