package me.bycoba.getevenhigher.main.commands

import me.bycoba.getevenhigher.main.manager.PluginManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GetEvenHigherHelpCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(PluginManager.PluginConfig.isNotAPlayerError.notAPlayer)
            return true
        }

        sender.sendMessage("---=GetEvenHigherPlus=--- \n Commands: \n /drugs \n /coke \n /Joint \n /lsd \n ---=GetEvenHigherPlus=--- ")

        return true
    }
}
