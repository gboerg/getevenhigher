package me.bycoba.getevenhigher.main.drugs.interaction

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

        if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.itemMeta?.displayName == "§2Almost Sugar") {
            player.sendActionBar("§3Drücke die Rechte Maustaste während du NICHT auf einen Block schaust")
            event.isCancelled = true
        }
    }

    fun onCoke (event: PlayerInteractEvent)  {
        val interactionManager = InteractionManager()
        val player = event.player
        val item = event.item ?: return
        val coke = "§2Almost Sugar"

        if (item.itemMeta?.displayName != "§2Almost Sugar" || event.action != Action.RIGHT_CLICK_AIR) {
            return
        }

        player.playSound(player.location, Sound.BLOCK_SAND_FALL, 1.0f, 1.0f)
        player.addPotionEffect(PotionEffectType.SPEED.createEffect(1000, 1))
        player.addPotionEffect(PotionEffectType.SATURATION.createEffect(420, 69))
        player.addPotionEffect(PotionEffectType.STRENGTH.createEffect(420, 69))
        InventoryManager.removeItemByName(player, coke)

        val meta = item.itemMeta
        if (meta != null) {
            meta.lore = listOf(
                "Looks like regular sugar",
                "- it reliefs pain tho",
                "§7 uses left",
                "§7 safe uses left"
            )
            item.itemMeta = meta
        }
    }
}