package me.bycoba.getevenhigher.main.validator

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import kotlin.random.Random

class SpawnValidator {

    companion object {
        private val playerSafeLocations = mutableMapOf<Player, Location>()

        private val safeNetherBlocks = listOf(
            Material.NETHERRACK,
            Material.BASALT,
            Material.BLACKSTONE,
            Material.CRIMSON_NYLIUM,
            Material.WARPED_NYLIUM,
            Material.SOUL_SAND,
            Material.SOUL_SOIL,
        )

        fun scheduleNetherSpawnSearch(player: Player, plugin: JavaPlugin) {
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                val netherLocation = getNetherEquivalentLocation(player.location)
                val safeLocation = findRandomSafeLocationInNether(netherLocation, 250)
                playerSafeLocations[player] = safeLocation
            }, 0L)
        }

        fun teleportToSafeNetherLocation(player: Player) {
            val safeLocation = playerSafeLocations[player]
            if (safeLocation != null) {
                player.teleport(safeLocation)
                playerSafeLocations.remove(player)
            }
        }

        fun findRandomSafeLocationInNether(startLocation: Location, radius: Int): Location {
            val world = startLocation.world ?: return startLocation

            repeat(100) {  // Versuche bis zu 100 verschiedene Positionen
                val randomLocation = getRandomLocation(startLocation, radius)
                if (isSafeNetherLocation(randomLocation)) {
                    return randomLocation.add(0.0, 1.0, 0.0) // Setze den Spieler auf den sicheren Boden
                }
            }
            return startLocation // Falls keine sichere Position gefunden wird, kehre zum Startort zurück
        }

        fun findRandomSafeLocationInEnd(startLocation: Location, radius: Int): Location {
            val world = startLocation.world ?: return startLocation

            repeat(100) {  // Versuche bis zu 100 verschiedene Positionen
                val randomLocation = getRandomLocation(startLocation, radius)
                if (isSafeEndLocation(randomLocation)) {
                    return randomLocation.add(0.0, 1.0, 0.0)
                }
            }
            return startLocation // Falls keine sichere Position gefunden wird, kehre zum Startort zurück
        }

        private fun getRandomLocation(startLocation: Location, radius: Int): Location {
            val randomX = startLocation.x + Random.nextInt(-radius, radius).toDouble()
            val randomZ = startLocation.z + Random.nextInt(-radius, radius).toDouble()
            val randomY = startLocation.y + Random.nextInt(-5, 5).toDouble() // Zufällige Höhe in einem Bereich von ±5 Blöcken
            return Location(startLocation.world, randomX, randomY, randomZ)
        }

        private fun isSafeNetherLocation(location: Location): Boolean {
            val blockBelow = location.clone().subtract(0.0, 1.0, 0.0).block
            val blockAtLocation = location.block
            val blockAbove = location.clone().add(0.0, 1.0, 0.0).block

            return blockBelow.type in safeNetherBlocks &&
                    blockAtLocation.type == Material.AIR &&
                    blockAbove.type == Material.AIR
        }

        private fun isSafeEndLocation(location: Location): Boolean {
            val blockBelow = location.clone().subtract(0.0, 1.0, 0.0).block
            val blockAtLocation = location.block
            val blockAbove = location.clone().add(0.0, 1.0, 0.0).block

            return blockBelow.type == Material.END_STONE &&
                    blockAtLocation.type == Material.AIR &&
                    blockAbove.type == Material.AIR
        }

        fun getNetherEquivalentLocation(overworldLocation: Location): Location {
            val netherWorld = Bukkit.getWorld("world_nether") ?: return overworldLocation
            val netherX = overworldLocation.x / 8.0
            val netherZ = overworldLocation.z / 8.0
            return Location(netherWorld, netherX, overworldLocation.y, netherZ)
        }
    }
}
