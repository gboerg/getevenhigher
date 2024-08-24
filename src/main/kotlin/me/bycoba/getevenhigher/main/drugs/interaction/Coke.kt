package me.bycoba.getevenhigher.main.drugs.interaction

import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.InteractionManager
import me.bycoba.getevenhigher.main.manager.InventoryManager
import org.bukkit.Sound
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffectType

class Coke (private val plugin: JavaPlugin) {
    private val interactionManager = InteractionManager()

    fun onCokePlace (event: BlockPlaceEvent) {
        val player = event.player
        val itemInHand = event.itemInHand

        if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.itemMeta?.displayName == DrugManager.DrugConfig.Coke.displayName) {
            player.sendActionBar(DrugManager.DrugConfig.Coke.actionBarOnFailedUse)
            event.isCancelled = true
        }
    }

    fun onCoke (event: PlayerInteractEvent)  {
        val interactionManager = InteractionManager()
        val player = event.player
        val item = event.item ?: return

        if (item.itemMeta?.displayName != DrugManager.DrugConfig.Coke.displayName || event.action != Action.RIGHT_CLICK_AIR) {
            return
        }

        player.playSound(player.location, Sound.BLOCK_SAND_FALL, 1.0f, 1.0f)
        player.addPotionEffect(PotionEffectType.SPEED.createEffect(1000, 1))
        player.addPotionEffect(PotionEffectType.SATURATION.createEffect(420, 69))
        player.addPotionEffect(PotionEffectType.STRENGTH.createEffect(420, 69))
        InventoryManager.removeItemByName(player, DrugManager.DrugConfig.Coke.displayName)

        val meta = item.itemMeta
        if (meta != null) {
            meta.lore = DrugManager.DrugConfig.Coke.lore
            item.itemMeta = meta
        }
    }
}