package me.bycoba.getevenhigher.main.drugs.interaction

import me.bycoba.getevenhigher.main.GetEvenHigher
import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.InventoryManager
import me.bycoba.getevenhigher.main.tasks.RunTaskLater
import me.bycoba.getevenhigher.main.tasks.TaskSpecific
import me.bycoba.getevenhigher.main.validator.SpawnValidator
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType



class LSD(private val plugin: JavaPlugin) {

    fun onLSDPlace(event: BlockPlaceEvent) {
        val player = event.player
        val itemInHand = event.itemInHand

        if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.itemMeta?.displayName == DrugManager.DrugConfig.LSD.displayName) {
            player.sendActionBar(DrugManager.DrugConfig.LSD.actionBarOnFailedUse)
            event.isCancelled = true
        }
    }

    fun onLSD(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return
        if (item.itemMeta?.displayName != DrugManager.DrugConfig.LSD.displayName || event.action != Action.RIGHT_CLICK_AIR) {
            return
        }
        player.setMetadata("LSD_EFFECT", FixedMetadataValue(plugin, true))

        val originalLocation = player.location.clone()  // Speichert den ursprünglichen Standort
        val deathLocation = player.location.clone()
        player.sendActionBar(DrugManager.DrugConfig.LSD.actionBar001)
        InventoryManager.removeItemByName(player, DrugManager.DrugConfig.LSD.displayName)

        // Vorzeitige Suche nach sicherem Spawnpunkt im Nether
        SpawnValidator.scheduleNetherSpawnSearch(player, plugin)

        // Effekte anwenden und nach Verzögerung teleportieren
        RunTaskLater.scheduleTask(plugin, 100L) {
            if (player.isOnline) {
                applyLsdEffects(player, originalLocation)
            }
        }

        RunTaskLater.scheduleTask(plugin, 200L) {
            if (player.isOnline) {
                simulatePlayerDeath(player, deathLocation)
            }
        }
    }
    private fun simulatePlayerDeath(player: Player, deathLocation: Location) {
        // Spieler wirklich sterben lassen, um das PlayerDeathEvent auszulösen
        player.health = 0.0  // Dies löst das PlayerDeathEvent automatisch aus

        // Wiederbelebung nach Todesbildschirm
        RunTaskLater.scheduleTask(plugin, 60L) {  // Verzögerung, um den Todesbildschirm anzuzeigen
            if (player.isOnline) {
                player.spigot().respawn()
                player.health = player.maxHealth / 2  // Setze die Gesundheit auf die Hälfte
                player.teleport(deathLocation)  // Spieler an den Todesort teleportieren
            }
        }
    }

    private fun applyLsdEffects(player: Player, originalLocation: Location) {
        player.addPotionEffect(PotionEffect(PotionEffectType.NAUSEA, 1000, 1))
        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 420, 69))
        player.addPotionEffect(PotionEffect(PotionEffectType.LUCK, 420, 69))

        player.sendActionBar("§2What a funky feelin'")
        RunTaskLater.scheduleTask(plugin, 100L) {
            if (player.isOnline) {
                // Teleportiere den Spieler zum sicheren Punkt im Nether
                SpawnValidator.teleportToSafeNetherLocation(player)
                player.sendActionBar("Well, strange place")

                // Weitere Effekte und Teleportation in die Endwelt nach einer Verzögerung
                RunTaskLater.scheduleTask(plugin, 300L) {
                    if (player.isOnline) {
                        teleportToEndAndBack(player, originalLocation)
                    }
                }
            }
        }
    }

    private fun teleportToEndAndBack(player: Player, originalLocation: Location) {
        val endWorld = Bukkit.getWorld("world_the_end")
        if (endWorld != null) {
            val endWorldSpawnLocation = SpawnValidator.findRandomSafeLocationInEnd(endWorld.spawnLocation, 50)
            player.teleport(endWorldSpawnLocation)
            player.sendActionBar("Uhh, what")

            RunTaskLater.scheduleTask(plugin, 1000L) {
                if (player.isOnline) {
                    player.teleport(originalLocation)  // Zurück zur ursprünglichen Location in der Overworld
                    player.sendActionBar("....")
                }
            }
        }
    }
}

