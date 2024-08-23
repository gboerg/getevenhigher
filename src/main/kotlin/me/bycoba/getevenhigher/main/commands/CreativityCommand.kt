package me.bycoba.getevenhigher.main.commands

import net.minecraft.server.commands.GameModeCommand
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class CreativityCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player to execute this command!")
            return true
        }
        val player = sender
        if (!player.hasPermission("getevenhigher.gamemode.creative")) {
            player.sendMessage("Insufficient permission")
            return true
        }
        player.gameMode = GameMode.CREATIVE
        player.sendMessage("§a[GetEvenHigher] §3It's time to be §ecreative §3now!")

        return true
    }
}
