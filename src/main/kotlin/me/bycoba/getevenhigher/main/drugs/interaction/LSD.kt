package me.bycoba.getevenhigher.main.drugs.interaction

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
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType



class LSD(private val plugin: JavaPlugin) {

    private fun isStrangePaper(item: ItemStack): Boolean {
        val meta = item.itemMeta
        return meta != null && meta.hasDisplayName() && meta.displayName == "§5S§eTRaN §bGE §cPaP §aeR"
    }

    private fun removeStrangePaper(player: Player) {
        val inventory = player.inventory
        inventory.contents.filterNotNull()
            .filter { item -> isStrangePaper(item) }
            .forEach { item -> inventory.remove(item) }
    }

    fun onLSDPlace(event: BlockPlaceEvent) {
        val player = event.player
        val itemInHand = event.itemInHand

        if (itemInHand != null && itemInHand.hasItemMeta() && itemInHand.itemMeta?.displayName == "§5S§eTRaN §bGE §cPaP §aeR") {
            player.sendActionBar("§3Drücke die Rechte Maustaste während du NICHT auf einen Block schaust")
            event.isCancelled = true
        }
    }

    fun onLSD(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item ?: return
        if (!isStrangePaper(item) || event.action != Action.RIGHT_CLICK_AIR) {
            return
        }

        val originalLocation = player.location.clone()  // Speichert den ursprünglichen Standort
        player.sendActionBar("§2Likin' Paper is quite weird - but I guess you should try - Right?")
        removeStrangePaper(player)

        // Vorzeitige Suche nach sicherem Spawnpunkt im Nether
        SpawnValidator.scheduleNetherSpawnSearch(player, plugin)

        // Effekte anwenden und nach Verzögerung teleportieren
        RunTaskLater.scheduleTask(plugin, 100L) {
            if (player.isOnline) {
                applyLsdEffects(player, originalLocation)
            }
        }
    }
    private fun applyLsdfakedeath(player: Player, event: PlayerDeathEvent) {

        // Setze den Todesbildschirm nicht anzeigen
        event.keepInventory = true
        event.keepLevel = true
        event.drops.clear()
        event.deathMessage = "${player.name} Imagined Death! Must be a bad trip "

        // Verzögere die Wiederbelebung um ein paar Ticks (optional)
        /*Bukkit.getScheduler().scheduleSyncDelayedTask(this, {
            // Wiederbeleben des Spielers mit halber Gesundheit
            player.spigot().respawn()
            player.health = player.maxHealth / 2
            player.teleport(event.entity.location) // Spieler an Todesort zurück teleportieren
        }, 1L)

       TaskSpecific.scheduleDelayedTask(this, 1L) {
            // Wiederbeleben des Spielers mit halber Gesundheit
            player.spigot().respawn()
            player.health = player.maxHealth / 2
            player.teleport(event.entity.location) // Spieler an Todesort zurück teleportieren
        }



         */

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

