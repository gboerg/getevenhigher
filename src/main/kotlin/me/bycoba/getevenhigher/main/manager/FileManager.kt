package me.bycoba.getevenhigher.main.manager

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File

object FileManager {

    /**
     * Example Usage:
     *
     * val config = FileManager.getFileConfiguration(GetEvenHigher.instance, "test.yml") // config holen
     * val string = config.getString("this.is.a.path") // Wert aus config mit einem Pfad holen, . als Trenner
     *
     * string = "this is the new string to be saved" // value string überschreiben
     * config.set("this.is.a.path", string) // neuer string wert für die config setzen
     * FileManager.saveFileConfiguration(GetEvenHigher.instance, "test.yml", config) // neue config speichern
     *
     * Man kann auch einen Wert setzen ohne ihn vorher zu lesen.
     * TODO: Video dazu ansehen
     *
     */

    fun createFile(plugin: Plugin, fileName: String): File {
        val pluginFolder = plugin.dataFolder

        if (!pluginFolder.exists())
            pluginFolder.mkdirs()

        val file = File(pluginFolder, fileName)
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return file
    }

    fun getFileConfiguration(plugin: Plugin, fileName: String): FileConfiguration {
        return YamlConfiguration.loadConfiguration(createFile(plugin, fileName))
    }

    fun saveFileConfiguration(plugin: Plugin, fileName: String, config: FileConfiguration) {
        try {
            config.save(createFile(plugin, fileName))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}