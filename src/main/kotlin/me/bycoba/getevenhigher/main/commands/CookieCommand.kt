package me.bycoba.getevenhigher.main.commands

import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.PluginManager
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
            sender.sendMessage(PluginManager.PluginConfig.isNotAPlayerError.notAPlayer)
            return true
        }

        val player = sender as Player

        val item = ItemStack(Material.COOKIE)
        val meta: ItemMeta? = item.itemMeta

        if (meta != null) {
            meta.setDisplayName(DrugManager.DrugConfig.Cookie.displayName)
            meta.lore = DrugManager.DrugConfig.Cookie.lore
            meta.hasEnchantmentGlintOverride()
            meta.setEnchantmentGlintOverride(true)
            meta.setCustomModelData((Math.random() * 100000).toInt())
            item.itemMeta = meta
        }

        player.inventory.addItem(item)
        player.sendActionBar(DrugManager.DrugConfig.Cookie.actionBarOnReceive)

        return true
    }
}
