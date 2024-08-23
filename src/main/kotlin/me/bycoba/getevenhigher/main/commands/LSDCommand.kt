package me.bycoba.getevenhigher.main.commands

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
            sender.sendMessage("You must be a player to execute this command!")
            return true
        }

        val player = sender as Player

        val item = ItemStack(Material.PAPER)
        val meta: ItemMeta? = item.itemMeta

        if (meta != null) {
            meta.setDisplayName("§5S§eTRaN §bGE §cPaP §aeR")
            meta.lore = listOf("Paper with a strong mind connection drawing")
            //meta.addEnchant(Enchantment., 1, true)
            meta.hasEnchantmentGlintOverride()
            meta.setEnchantmentGlintOverride(true)

            item.itemMeta = meta
        }

        player.inventory.addItem(item)
        player.sendActionBar("You have received a paper with a drawing on it!")

        return true
    }
}
