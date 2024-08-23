package me.bycoba.getevenhigher.main.commands

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class DebugCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player to execute this command!")
            return true
        }

        val player = sender as Player

        if (!player.hasPermission("getevenhigher.server.debug.plugin.reload")) {
            player.sendMessage("Insufficient permission")
            return true
        }
        player.sendMessage("§l§6[DEBUG ASSISTANT] §8| §3DEBUG PLUGIN RELOAD : May cause issues!")
        player.performCommand("reload confirm")


        return true
    }
}