package me.bycoba.getevenhigher.main.commands

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class CookieCommand: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage("You must be a player to execute this command!")
            return true
        }

        val player = sender as Player

        val item = ItemStack(Material.COOKIE)
        val meta: ItemMeta? = item.itemMeta

        if (meta != null) {
            meta.setDisplayName("§2Unusual Cookie")
            meta.lore = listOf("Looks like an usual cookie but it smells different")
            meta.hasEnchantmentGlintOverride()
            meta.setEnchantmentGlintOverride(true)

            item.itemMeta = meta
        }

        player.inventory.addItem(item)
        player.sendActionBar("You have received a special cookie!")

        return true
    }
}
