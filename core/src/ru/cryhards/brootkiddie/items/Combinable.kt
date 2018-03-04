package ru.cryhards.brootkiddie.items

/**
 * Marks object that can combine items
 */
interface Combinable {
    /**
     * Produces new item based on combination input
     */
    fun combine(item: Item): Item
}