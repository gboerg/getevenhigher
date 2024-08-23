package me.bycoba.getevenhigher.main.tasks

import me.bycoba.getevenhigher.main.GetEvenHigher
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable

class ItemInteractionTask(private val plugin: GetEvenHigher) : BukkitRunnable() {
    override fun run() {
        Bukkit.getOnlinePlayers().forEach { player ->
            val interactionManager = plugin.getInteractionManager()
            val itemToCheck = ItemStack(Material.SUGAR) // Ersetze dies durch das relevante Item

            // Überprüfe, ob der Spieler das Item im Inventar hat
            if (player.inventory.contains(itemToCheck)) {
                // Führe die Interaktion aus, falls notwendig
               // interactionManager.enforceItemInteraction(player)
            }
        }
    }
}