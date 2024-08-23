package me.bycoba.getevenhigher.main.drugs.customEffects

import org.bukkit.Bukkit
import org.bukkit.entity.*
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class MobAttack (private val plugin: JavaPlugin ) {

    private val aggressionMobMode = mutableSetOf<Player>()

    private val passiveMobs = listOf(
        EntityType.BAT,
        EntityType.BEE,
        EntityType.CAT,
        EntityType.CHICKEN,
        EntityType.COD,
        EntityType.COW,
        EntityType.DONKEY,
        EntityType.FOX,
        EntityType.FROG,
        EntityType.HORSE,
        EntityType.MOOSHROOM,
        EntityType.MULE,
        EntityType.OCELOT,
        EntityType.PARROT,
        EntityType.PIG,
        EntityType.RABBIT,
        EntityType.SALMON,
        EntityType.SHEEP,
        EntityType.SKELETON_HORSE,
        EntityType.SNOW_GOLEM,
        EntityType.SQUID,
        EntityType.STRIDER,
        EntityType.TADPOLE,
        EntityType.TROPICAL_FISH,
        EntityType.TURTLE,
        EntityType.VILLAGER,
        EntityType.WANDERING_TRADER
    )

    private val aggressiveMobs = listOf(
        EntityType.BLAZE,
        EntityType.CAVE_SPIDER,
        EntityType.CREEPER,
        EntityType.DROWNED,
        EntityType.ELDER_GUARDIAN,
        EntityType.ENDERMAN,
        EntityType.ENDERMITE,
        EntityType.EVOKER,
        EntityType.GHAST,
        EntityType.GUARDIAN,
        EntityType.HOGLIN,
        EntityType.HUSK,
        EntityType.ILLUSIONER,
        EntityType.MAGMA_CUBE,
        EntityType.PHANTOM,
        EntityType.PIGLIN,
        EntityType.PIGLIN_BRUTE,
        EntityType.PILLAGER,
        EntityType.RAVAGER,
        EntityType.SHULKER,
        EntityType.SILVERFISH,
        EntityType.SKELETON,
        EntityType.SLIME,
        EntityType.SPIDER,
        EntityType.STRAY,
        EntityType.VEX,
        EntityType.VINDICATOR,
        EntityType.WARDEN,
        EntityType.WITCH,
        EntityType.WITHER_SKELETON,
        EntityType.ZOGLIN,
        EntityType.ZOMBIE,
        EntityType.ZOMBIE_VILLAGER,
        EntityType.ZOMBIFIED_PIGLIN
    )


    fun onEntityTarget(event: EntityTargetEvent) {
        val entity = event.entity
        val target = event.target

        if (target is Player && aggressionMobMode.contains(target) && isAggressiveOrPassiveMob(entity) && isWithinRadius(entity as LivingEntity, target, 50.0)) {
            event.isCancelled = false
        }
    }

    fun aggressiveMobs(player: Player) {
        aggressionMobMode.add(player)
        Bukkit.getScheduler().runTask(plugin, Runnable {
            player.world.entities.forEach { entity ->
                if (entity is LivingEntity && isAggressiveOrPassiveMob(entity) && isWithinRadius(entity, player, 50.0)) {
                    if (entity.type in passiveMobs) {
                        // Passive Mobs sollen aggressiv werden
                        if (entity is Creature) {
                            entity.target = player
                        } else if (entity is Wolf) {
                            entity.target = player // Wolf wird sofort aggressiv
                        } else if (entity is IronGolem) {
                            entity.target = player // Iron Golem greift sofort an
                        } else if (entity is Villager) {
                            // Simuliere einen Villager-Angriff
                            simulateVillagerAttack(entity as Villager, player)
                        }
                    }
                }
            }

            // Regelmäßige Überprüfung einrichten
            object : BukkitRunnable() {
                override fun run() {
                    player.world.entities.forEach { entity ->
                        if (entity is LivingEntity && isAggressiveOrPassiveMob(entity) && isWithinRadius(entity, player, 50.0)) {
                            if (entity.type in passiveMobs || entity.type in aggressiveMobs) {
                                if (entity is Creature) {
                                    if (entity.target == player) {
                                        entity.target = player
                                    }
                                } else if (entity is Wolf) {
                                    entity.target = player
                                } else if (entity is IronGolem) {
                                    entity.target = player
                                } else if (entity is Villager) {
                                    simulateVillagerAttack(entity as Villager, player)
                                }
                            }
                        }
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L) // Alle 20 Ticks (1 Sekunde)
        })
    }



    fun restoreDefaultMobBehaviour(player: Player) {
        aggressionMobMode.remove(player)
        player.sendActionBar("§4Mob verhalten zurückgesetzt.")
    }
/*
    fun isAggressiveOrPassiveMob(entity: Entity): Boolean {
        return when (entity.type) {
            EntityType.ZOMBIE, EntityType.SKELETON, EntityType.CREEPER,
            EntityType.SPIDER, EntityType.ENDERMAN, EntityType.COW,
            EntityType.SHEEP, EntityType.CHICKEN, EntityType.PIG,
            EntityType.HORSE, EntityType.WOLF, EntityType.OCELOT,
            EntityType.IRON_GOLEM, EntityType.SNOW_GOLEM, EntityType.VILLAGER -> true
            else -> false
        }
    }

 */
    fun isAggressiveOrPassiveMob(entity: Entity): Boolean {
        return entity.type in aggressiveMobs || entity.type in passiveMobs
    }

    fun isWithinRadius(entity: LivingEntity, player: Player, radius: Double): Boolean {
        return entity.location.distance(player.location) <= radius
    }

    fun simulateVillagerAttack(villager: Villager, player: Player) {
        // Beispiel: Villager schießt einen Schneeball auf den Spieler
        val snowball = villager.launchProjectile(Snowball::class.java)
        snowball.velocity = player.location.subtract(villager.location).toVector().normalize().multiply(1.5)
    }

}
