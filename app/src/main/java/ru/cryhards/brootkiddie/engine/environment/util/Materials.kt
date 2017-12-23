package ru.cryhards.brootkiddie.engine.environment.util

/**
 * Manages object material presets
 *
 * Created with love by luna_koly on 23.12.2017.
 */
enum class Materials {
    /**
     * Pixels with alpha = 0 are replaced
     * by material self color
     */
    STICKER,
    /**
     * Pixels with alpha = 0 are treated
     * as transparent surface part
     */
    SKIN
}