package me.bycoba.getevenhigher.main.manager

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffectType
import java.util.concurrent.ConcurrentHashMap

class InteractionManager {

    private val interactionCounts = ConcurrentHashMap<Player, Int>()
    private val interactionLimits = ConcurrentHashMap<Player, Int>()

    private val itemUsesLeft = ConcurrentHashMap<Player, Int>()
    private val itemSafeUsesLeft = ConcurrentHashMap<Player, Int>()

    private val totalSafeInteractionCount = ConcurrentHashMap<Player, Int>()
    private val totalSafeInteractionLimit = ConcurrentHashMap<Player, Int>()



    fun removeItem(player: Player, itemStack: ItemStack, amount: Int) {
        val inventory = player.inventory
        var amountToRemove = amount

        for (i in inventory.contents.indices) {
            val item = inventory.contents[i] ?: continue
            if (item.isSimilar(itemStack)) {
                if (item.amount <= amountToRemove) {
                    amountToRemove -= item.amount
                    inventory.clear(i)
                    if (amountToRemove <= 0) {
                        break
                    }
                } else {
                    item.amount -= amountToRemove
                    amountToRemove = 0
                    break
                }
            }
        }
    }

    companion object {
        fun removePositiveEffects(player: Player) {
            val positiveEffects = listOf(
                PotionEffectType.SPEED,
                PotionEffectType.HASTE,
                PotionEffectType.STRENGTH,
                PotionEffectType.JUMP_BOOST,
                PotionEffectType.REGENERATION,
                PotionEffectType.RESISTANCE,
                PotionEffectType.FIRE_RESISTANCE,
                PotionEffectType.WATER_BREATHING,
                PotionEffectType.INVISIBILITY,
                PotionEffectType.NIGHT_VISION,
                PotionEffectType.HEALTH_BOOST,
                PotionEffectType.ABSORPTION,
                PotionEffectType.SATURATION,
                PotionEffectType.LUCK,
                PotionEffectType.HERO_OF_THE_VILLAGE
            )

            player.activePotionEffects.forEach { effect ->
                if (positiveEffects.contains(effect.type)) {
                    player.removePotionEffect(effect.type)
                }
            }
        }

        fun removeNegativeEffects(player: Player) {
            val negativeEffects = listOf(
                PotionEffectType.BLINDNESS,
                PotionEffectType.NAUSEA,
                PotionEffectType.HUNGER,
                PotionEffectType.INSTANT_DAMAGE,
                PotionEffectType.POISON,
                PotionEffectType.SLOWNESS,
                PotionEffectType.MINING_FATIGUE,
                PotionEffectType.WEAKNESS,
                PotionEffectType.WITHER,
                PotionEffectType.UNLUCK,
                PotionEffectType.LEVITATION,
                PotionEffectType.BAD_OMEN,
                PotionEffectType.DARKNESS
            )

            player.activePotionEffects.forEach { effect ->
                if (negativeEffects.contains(effect.type)) {
                    player.removePotionEffect(effect.type)
                }
            }
        }
    }
}
