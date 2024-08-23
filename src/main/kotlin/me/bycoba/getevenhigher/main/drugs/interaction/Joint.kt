package me.bycoba.getevenhigher.main.drugs.interaction

import me.bycoba.getevenhigher.main.drugs.customEffects.MobIgnore
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
        val player = event.player
        val itemInHand = event.itemInHand

        if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.itemMeta?.displayName == "§2Herb in Paper") {
            player.sendActionBar("§3Drücke die Rechte Maustaste während du NICHT auf einen Block schaust")
            event.isCancelled = true
        }
    }

    fun onJoint(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return
        val joint = "§2Herb in Paper"

        if (item.itemMeta?.displayName != "§2Herb in Paper" || event.action != Action.RIGHT_CLICK_AIR) {
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
        InventoryManager.removeItemByName(player, joint)


        // Aktualisiere die Lore des Items
        val meta = item.itemMeta
        if (meta != null) {
            meta.lore = listOf(
                "An unknown herb rolled in something paperlike",
                "- smells interesting tho",
                "§7 uses left"
            )
            item.itemMeta = meta
            meta.lore
        }

        // Plane das Zurücksetzen der Interaktionsanzahl und das Ignorieren der Mobs
        RunTaskLater.scheduleTask(plugin, 600L, Runnable { mobIgnore.unignoreMobs(player) })
    }
}
