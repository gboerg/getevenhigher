import me.bycoba.getevenhigher.main.manager.PluginManager
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GEHCommandCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(PluginManager.PluginConfig.isNotAPlayerError.notAPlayer)
            return true
        }

        // Überprüfen, ob der Befehl "drugs" ausgeführt wird
        if (label.equals("geh", ignoreCase = true)) {

            sender.sendMessage("ULTIMATE TEXT DEBUGGIN'")

            // Erstellen und Senden der ersten Textkomponente
            val jointMessage = TextComponent("Click here to get a Joint")
            jointMessage.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/joint")
            jointMessage.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("Get a Joint"))
            sender.spigot().sendMessage(jointMessage)

            // Erstellen und Senden der zweiten Textkomponente
            val lsdMessage = TextComponent("Click here to get LSD")
            lsdMessage.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lsd")
            lsdMessage.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("Get LSD"))
            sender.spigot().sendMessage(lsdMessage)

            // Erstellen und Senden der dritten Textkomponente
            val cookieMessage = TextComponent("Click here to get a Cookie")
            cookieMessage.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cookie")
            cookieMessage.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("Get a Cookie"))
            sender.spigot().sendMessage(cookieMessage)

            // Erstellen und Senden der vierten Textkomponente
            val cokeMessage = TextComponent("Click here to get Coke")
            cokeMessage.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "/coke")
            cokeMessage.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("Get Coke"))
            sender.spigot().sendMessage(cokeMessage)
        }
        return true
    }
}
