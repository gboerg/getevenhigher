package me.bycoba.getevenhigher.main.drugs.customEffects

import me.bycoba.getevenhigher.main.manager.InteractionManager
import org.bukkit.Bukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class MobIgnore (private val plugin: JavaPlugin) {

    private val ignoreMobMode = mutableSetOf<Player>()

    fun onEntityTarget(event: EntityTargetEvent) {
        val entity = event.entity
        val target = event.target

        if (target is Player && ignoreMobMode.contains(target) && isAggressiveOrPassiveMob(entity) && isWithinRadius(entity as LivingEntity, target, 50.0)) {
            event.isCancelled = true
        }
        }

    fun ignoreMobs(player: Player) {
        ignoreMobMode.add(player)
        Bukkit.getScheduler().runTask(plugin, Runnable {
            player.world.entities.forEach { entity ->
                if (entity is LivingEntity && isAggressiveOrPassiveMob(entity) && isWithinRadius(entity, player, 50.0)) {
                    if (entity is org.bukkit.entity.Creature) {
                        entity.target = null
                        entity.isPersistent = true
                    }
                }
            }

            // Regelmäßige Überprüfung einrichten
            object : BukkitRunnable() {
                override fun run() {
                    player.world.entities.forEach { entity ->
                        if (entity is LivingEntity && isAggressiveOrPassiveMob(entity) && isWithinRadius(entity, player, 50.0)) {
                            if (entity is org.bukkit.entity.Creature) {
                                if (entity.target == player) {
                                    entity.target = null
                                }
                            }
                        }
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L) // Alle 20 Ticks (1 Sekunde)
        })
    }

    fun unignoreMobs(player: Player) {
        ignoreMobMode.remove(player)
        player.sendActionBar("§4Die Mobs reagieren jetzt wieder auf dich.")
    }

    fun isAggressiveOrPassiveMob(entity: Entity): Boolean {
        return when (entity.type) {
            EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER,
            EntityType.SPIDER, EntityType.ENDERMAN, EntityType.COW,
            EntityType.SHEEP, EntityType.CHICKEN, EntityType.PIG,
            EntityType.HORSE, EntityType.WOLF, EntityType.OCELOT -> true
            else -> false
        }
    }

    fun isWithinRadius(entity: LivingEntity, player: Player, radius: Double): Boolean {
        return entity.location.distance(player.location) <= radius
    }
}