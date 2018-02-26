package ru.cryhards.brootkiddie.items.custom

import com.badlogic.gdx.graphics.Texture
import ru.cryhards.brootkiddie.items.Script
import ru.cryhards.brootkiddie.items.effects.MiningEffect

/**
 * Created by user on 2/26/18.
 */

class MiningScript : Script("Miner", "Mines crypto for U", Texture("img/items/miner.png"), 10f) {
    init {
        this.plus(MiningEffect())
    }
}