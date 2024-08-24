package me.bycoba.getevenhigher.main.commands

import me.bycoba.getevenhigher.main.manager.DrugManager
import me.bycoba.getevenhigher.main.manager.DrugManager.DrugConfig.Joint.lore
import me.bycoba.getevenhigher.main.manager.InteractionManager
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
            sender.sendMessage("You must be a player to execute this command!")
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
            item.itemMeta = meta
        }

        player.inventory.addItem(item)
        player.sendActionBar("You have received a Joint!")


        return true
    }
}
