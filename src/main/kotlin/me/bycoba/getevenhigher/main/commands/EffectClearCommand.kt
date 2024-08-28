
package me.bycoba.getevenhigher.main.commands

import me.bycoba.getevenhigher.main.manager.PluginManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

class EffectClearCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage(PluginManager.PluginConfig.isNotAPlayerError.notAPlayer)
            return true
        }

        val player = sender as Player

        if (!player.hasPermission("getevenhigher.debug.effect.clearall")) {
            player.sendMessage("Insufficient permission")
            return true
        }
        player.activePotionEffects.forEach { effect ->
            player.removePotionEffect(effect.type)
        }

        //player.performCommand("effect clear")
        player.sendMessage("§l§6[ASSISTANT] §8| §3ALL EFFECTS CLEARED!")

        return true

    }
}