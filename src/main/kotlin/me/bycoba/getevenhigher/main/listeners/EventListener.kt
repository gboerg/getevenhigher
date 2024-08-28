package me.bycoba.getevenhigher.main.listeners

import io.papermc.paper.event.player.AsyncChatEvent
import me.bycoba.getevenhigher.main.drugs.customEffects.MobIgnore
import me.bycoba.getevenhigher.main.drugs.interaction.Coke
import me.bycoba.getevenhigher.main.drugs.interaction.Cookie
import me.bycoba.getevenhigher.main.drugs.interaction.Joint
import me.bycoba.getevenhigher.main.drugs.interaction.LSD
import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.InteractionManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityTargetEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerCommandSendEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class EventListener(private val plugin: JavaPlugin, private val interactionManager: InteractionManager) : Listener {

    private val joint = Joint(plugin)
    private val coke = Coke(plugin)
    private val cookie = Cookie(plugin)
    private val lsd = LSD(plugin)
    private val drugs = DrugManager()


    @EventHandler
    fun onEntityTarget(event: EntityTargetEvent) {
        val mobIgnore = MobIgnore(plugin)
        mobIgnore.onEntityTarget(event)
    }

    // Joint Interactions

    @EventHandler
    fun onJointPlace(event: BlockPlaceEvent) {
        joint.onJointPlace(event)
    }

    @EventHandler
    fun onJoint(event: PlayerInteractEvent) {
        joint.onJoint(event)
    }

    // Cookie Interactions

    @EventHandler
    fun onCookie(event: PlayerItemConsumeEvent) {
        cookie.onCookie(event)
    }

    @EventHandler
    fun onCookiePlace(event: BlockPlaceEvent) {
        cookie.onCookiePlace(event)
    }

    // Coke Interactions

    @EventHandler
    fun onCokeBlockPlace(event: BlockPlaceEvent) {
        coke.onCokePlace(event)
    }

    @EventHandler
    fun onCokeInteract(event: PlayerInteractEvent) {
        coke.onCoke(event)
    }

    // LSD Interactions

    @EventHandler
    fun onLSDPlace(event: BlockPlaceEvent) {
        lsd.onLSDPlace(event)
    }

    @EventHandler
    fun onLSDInteract(event: PlayerInteractEvent) {
        lsd.onLSD(event)
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        lsd.handlePlayerDeath(event)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        lsd.handlePlayerQuit(event)
    }
}
