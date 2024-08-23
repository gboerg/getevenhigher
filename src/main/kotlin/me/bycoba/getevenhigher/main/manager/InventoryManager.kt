package me.bycoba.getevenhigher.main.manager

import org.bukkit.entity.Player

class InventoryManager {

    companion object {
        fun removeItemByName(player: Player, nameToCheck: String) {
            val inventory = player.inventory
            for (item in inventory.contents) {
                if (item != null && item.itemMeta?.displayName == nameToCheck) {
                    inventory.remove(item) // Entfernt das Item mit dem spezifischen Display-Namen
                }
            }
        }
    }
}