package me.bycoba.getevenhigher.main.commands

import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.DrugManager.DrugConfig.LSD.displayName
import me.bycoba.getevenhigher.main.manager.DrugManager.DrugConfig.LSD.lore
import me.bycoba.getevenhigher.main.manager.PluginManager
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class LSDCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        if (sender !is Player) {
            sender.sendMessage(PluginManager.PluginConfig.isNotAPlayerError.notAPlayer)
            return true
        }

        val player = sender as Player

        val item = ItemStack(Material.PAPER)
        val meta: ItemMeta? = item.itemMeta

        if (meta != null) {
            meta.setDisplayName(displayName)
            meta.lore = lore
            //meta.addEnchant(Enchantment., 1, true)
            meta.hasEnchantmentGlintOverride()
            meta.setEnchantmentGlintOverride(true)
            meta.setCustomModelData((Math.random() * 100000).toInt())
            item.itemMeta = meta
        }

        player.inventory.addItem(item)
        player.sendActionBar(DrugManager.DrugConfig.LSD.actionBarOnReceive)

        return true
    }
}
