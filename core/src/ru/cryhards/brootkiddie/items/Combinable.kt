package ru.cryhards.brootkiddie.items

/**
 * Marks object that can combine items
 */
interface Combinable : Cloneable{
    /**
     * Produces new item based on combination input
     */
    fun combine(item: Item): Item

    public override fun clone(): Any {
        return super.clone()
    }
}