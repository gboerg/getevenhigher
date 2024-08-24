
package me.bycoba.getevenhigher.main.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EffectClearCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player to execute this command!")
            return true
        }

        val player = sender as Player

        if (!player.hasPermission("getevenhigher.debug.effect.clearall")) {
            player.sendMessage("Insufficient permission")
            return true
        }
        player.sendMessage("§l§6[ASSISTANT] §8| §3ALL EFFECTS CLEARED!")
        player.performCommand("effect clear")


        return true
    }
}