package me.bycoba.getevenhigher.main.drugs.interaction

import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.InventoryManager
import me.bycoba.getevenhigher.main.tasks.RunTaskLater
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class Cookie (private val plugin: JavaPlugin) {


    fun onCookiePlace (event: BlockPlaceEvent) {
        val player = event.player
        val itemInHand = event.itemInHand

        if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.itemMeta?.displayName == DrugManager.DrugConfig.Cookie.displayName) {
            player.sendActionBar(DrugManager.DrugConfig.Cookie.actionBarOnFailedUse)
            event.isCancelled = true
        }
    }

    fun onCookie(event: PlayerItemConsumeEvent) {
        val player = event.player
        val item = event.item ?: return
        val isUnusualCookie = item.itemMeta?.displayName == DrugManager.DrugConfig.Cookie.displayName
        player.sendActionBar(DrugManager.DrugConfig.Cookie.actionBar001)

        RunTaskLater.scheduleTask(plugin, 200L, Runnable {
            if (player.isOnline && isUnusualCookie) {
                player.playSound(player.location, Sound.BLOCK_CAMPFIRE_CRACKLE, 1.0f, 1.0f)
                player.spawnParticle(Particle.CLOUD, player.location, 60)
                player.foodLevel = 4
                player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, 1000, 1))
                player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 420, 69))
                player.addPotionEffect(PotionEffect(PotionEffectType.LUCK, 420, 69))
                player.addPotionEffect(PotionEffect(PotionEffectType.MINING_FATIGUE, 1000, 55))

                player.sendActionBar(DrugManager.DrugConfig.Cookie.actionBar002)
                InventoryManager.removeItemByName(player, DrugManager.DrugConfig.Cookie.displayName)
            }
        })
    }
}