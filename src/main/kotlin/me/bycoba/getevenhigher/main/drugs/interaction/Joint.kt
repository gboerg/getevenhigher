package me.bycoba.getevenhigher.main.drugs.interaction

import me.bycoba.getevenhigher.main.drugs.customEffects.MobIgnore
import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.InteractionManager
import me.bycoba.getevenhigher.main.manager.InventoryManager
import me.bycoba.getevenhigher.main.tasks.RunTaskLater
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffectType

class Joint(private val plugin: JavaPlugin) {

    private val mobIgnore = MobIgnore(plugin)


    fun onJointPlace(event: BlockPlaceEvent) {
        val jointDisplayName = DrugManager.DrugConfig.Joint.displayName
        val player = event.player
        val itemInHand = event.itemInHand

        if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.itemMeta?.displayName == jointDisplayName) {
            player.sendActionBar("§3Drücke die Rechte Maustaste während du NICHT auf einen Block schaust")
            event.isCancelled = true
        }
    }

    fun onJoint(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return
        val jointDisplayName = DrugManager.DrugConfig.Joint.displayName


        if (item.itemMeta?.displayName != jointDisplayName || event.action != Action.RIGHT_CLICK_AIR) {
            return
        }

        // Führe Effekte und Aktionen aus
        player.playSound(player.location, Sound.BLOCK_CAMPFIRE_CRACKLE, 1.0f, 1.0f)
        player.spawnParticle(Particle.CLOUD, player.location, 60)
        player.foodLevel = 4
        player.addPotionEffect(PotionEffectType.NAUSEA.createEffect(1000, 1))
        player.addPotionEffect(PotionEffectType.SLOW_FALLING.createEffect(420, 69))
        player.addPotionEffect(PotionEffectType.LUCK.createEffect(420, 69))
        player.addPotionEffect(PotionEffectType.MINING_FATIGUE.createEffect(1000, 55))
        InventoryManager.removeItemByName(player, jointDisplayName)


        // Aktualisiere die Lore des Items
        val meta = item.itemMeta
        if (meta != null) {
            // Verwende die Lore aus der Konfiguration
            meta.lore = DrugManager.DrugConfig.Joint.lore
            item.itemMeta = meta
        }

        // Plane das Zurücksetzen der Interaktionsanzahl und das Ignorieren der Mobs
        RunTaskLater.scheduleTask(plugin, 600L, Runnable { mobIgnore.unignoreMobs(player) })
    }
}
