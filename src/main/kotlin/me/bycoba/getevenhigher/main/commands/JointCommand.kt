package me.bycoba.getevenhigher.main.commands

import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.DrugManager.DrugConfig.Joint.lore
import me.bycoba.getevenhigher.main.manager.InteractionManager
import me.bycoba.getevenhigher.main.manager.PluginManager
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

class JointCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(PluginManager.PluginConfig.isNotAPlayerError.notAPlayer)
            return true
        }

        val player = sender as Player
        val interactionManager = InteractionManager()
        val item = ItemStack(Material.END_ROD)
        val meta: ItemMeta? = item.itemMeta
        val jointDisplayName = DrugManager.DrugConfig.Joint.displayName

        if (meta != null) {
            meta.setDisplayName(jointDisplayName)
            meta.lore = lore
            meta.hasEnchantmentGlintOverride()
            meta.setEnchantmentGlintOverride(true)
            meta.setCustomModelData((Math.random() * 100000).toInt())
            item.itemMeta = meta
        }

        player.inventory.addItem(item)
        player.sendActionBar(DrugManager.DrugConfig.Joint.actionBarOnReceive)


        return true
    }
}
