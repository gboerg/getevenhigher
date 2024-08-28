package me.bycoba.getevenhigher.main.manager

import io.papermc.paper.event.player.AsyncChatEvent
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.event.player.AsyncPlayerChatEvent

class DrugManager {

    object DrugConfig {
        object Coke {
            val displayName = "§4Almost Sugar"
            val safeUses = 5
            val maxUses = 10
            val lore = listOf(
                "Looks like regular sugar",
                "- it reliefs pain tho",
                "§7 uses left",
                "§7 safe uses left"
            )
            val actionBarOnFailedUse = "§3Drücke die Rechte Maustaste während du NICHT auf einen Block schaust"
            val actionBarOnReceive = "You have received some sugar!"

            val actionBar001 = "§2Hmm? Very Strange - Nothing happened - Maybe try another one"
            val actionBar002 = "§2What a funky feelin"
        }



        object Cookie {
            val displayName = "§2Unusual Cookie"
            val safeUses = 5
            val maxUses = 10
            val lore = listOf(
                "Looks like an usual cookie but it smells different"
            )
            val actionBarOnFailedUse = "§3Halte die Rechte Maustaste während du NICHT auf einen Block schaust"
            val actionBarOnReceive = "You have received a special cookie!"

            val actionBar001 = "§2Hmm? Very Strange - Nothing happened - Maybe try another one"
            val actionBar002 = "§2What a funky feelin"
        }



        object Joint {
            val displayName = "§2Herb in Paper"
            val safeUses = 3
            val maxUses = 6
            val lore = listOf(
                "An unknown herb rolled in something paperlike",
                "- smells interesting tho",
                "§7 uses left"
            )
            val actionBarOnFailedUse = "§3Drücke die Rechte Maustaste während du NICHT auf einen Block schaust"
            val actionBarOnReceive = "You have received some herb in a piece of paper!"

            val actionBar001 = "§2Hmm? Very Strange - Nothing happened - Maybe try another one"
            val actionBar002 = "§2What a funky feelin"
        }



        object LSD {
            val displayName = "§5S§eTRaN §bGE §cPaP §aeR"
            val safeUses = 2
            val maxUses = 4
            val lore = listOf(
                "Paper with a strong mind connection drawing"
            )
            val actionBarOnFailedUse = "§3Drücke die Rechte Maustaste während du NICHT auf einen Block schaust"
            val actionBarOnReceive = "You have received something very special!"

            val actionBar001 = "§2Likin' Paper is quite weird - but I guess you should try - Right?"
            val actionBar002 = "§2What a funky feelin"
        }



        fun getDrugDisplayName(drug: String): String? {
            return when (drug.lowercase()) {
                "coke" -> Coke.displayName
                "joint" -> Joint.displayName
                "lsd" -> LSD.displayName
                "cookie" -> Cookie.displayName
                else -> null
            }
        }
    }
}