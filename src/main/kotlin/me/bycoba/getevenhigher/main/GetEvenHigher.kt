package me.bycoba.getevenhigher.main

import me.bycoba.getevenhigher.main.commands.*
import me.bycoba.getevenhigher.main.drugs.customEffects.MobIgnore
import me.bycoba.getevenhigher.main.drugs.interaction.Coke
import me.bycoba.getevenhigher.main.drugs.interaction.Cookie
import me.bycoba.getevenhigher.main.drugs.interaction.Joint
import me.bycoba.getevenhigher.main.drugs.interaction.LSD
import me.bycoba.getevenhigher.main.listeners.ItemInteract
import me.bycoba.getevenhigher.main.manager.*
import org.bukkit.plugin.java.JavaPlugin

class GetEvenHigher : JavaPlugin() {

    private lateinit var fileManager: FileManager
    private lateinit var interactionManager: InteractionManager
    private lateinit var inventoryManager: InventoryManager
    private lateinit var addictionManager: AddictionManager
    private lateinit var cuttingAgentManager: CuttingAgentManager
    private lateinit var drugManager: DrugManager
    private lateinit var joint: Joint
    private lateinit var mobIgnore: MobIgnore
    private lateinit var coke: Coke
    private lateinit var cookie: Cookie
    private lateinit var lsd: LSD
    val reset = "\u001B[0m" // ANSI-Reset-Code, um die Farben wieder auf normal zu setzen
    val green = "\u001B[32m" // ANSI-Grün-Code
    val yellow = "\u001B[33m" // ANSI-Gelb-Code
    val red = "\u001B[31m" // ANSI-Rot-Code
    val blue = "\u001B[34m" // ANSI-Blau-Code
    val cyan = "\u001B[36m" // ANSI-Cyan-Code
    val gray = "\u001B[37m" // ANSI-Hellgrau-Code

    override fun onEnable() {
        // Plugin startup logic
        initializeManagersAndObjects()
        registerCommands()
        registerEvents()

        // Send Info to Console
        logger.info("${gray}---------------=GetEvenHigher=--------------$reset")
        logger.info("${yellow}Plugin Name: ${blue}${description.name}$reset")
        logger.info("${yellow}Version: ${blue}${description.version}$reset")
        logger.info("${yellow}Author: ${cyan}${description.authors.joinToString(", ")}$reset")
        logger.info("${yellow}Description: ${blue}${description.description}$reset")
        logger.info("${green}---------------=PLUGIN_INFO:=--------------$reset")
        logger.info("${green}${config.name} has been loaded successfully.$reset")
        logger.info("${green}${description.name} has been enabled successfully!$reset")
        logger.info("${gray}============================================$reset")
    }

    private fun initializeManagersAndObjects() {
        // Initialisiere Manager und Objekte
        fileManager = FileManager
        drugManager = DrugManager()
        addictionManager = AddictionManager()
        cuttingAgentManager = CuttingAgentManager()
        inventoryManager = InventoryManager()
        interactionManager = InteractionManager()
        mobIgnore = MobIgnore(this)
        joint = Joint(this)
        coke = Coke(this)
        cookie = Cookie(this)
        lsd = LSD(this)
    }

    private fun registerCommands() {
        // Registriere Befehle
        this.getCommand("joint")?.setExecutor(JointCommand())
        this.getCommand("cookie")?.setExecutor(CookieCommand())
        this.getCommand("lsd")?.setExecutor(LSDCommand())
        this.getCommand("coke")?.setExecutor(CokeCommand())
        this.getCommand("drugs")?.setExecutor(DrugsCommand())
        this.getCommand("creativity")?.setExecutor(CreativityCommand())
        this.getCommand("drl")?.setExecutor(DebugCommand())
        this.getCommand("ec")?.setExecutor(EffectClearCommand())
    }

    private fun registerEvents() {
        // Registriere Events
        server.pluginManager.registerEvents(ItemInteract(this, interactionManager), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("${gray}---------------=GetEvenHigher=--------------$reset")
        logger.info("${green}${config.name} has been saved successfully.$reset")
        logger.info("${red}${description.name} has been disabled.$reset")
        logger.info("${gray}============================================$reset")
    }

    fun getInteractionManager(): InteractionManager {
        return interactionManager
    }

    fun getInventoryManger(): InventoryManager {
        return inventoryManager
    }

    fun getDrugManager(): DrugManager {
        return drugManager
    }

    fun getCuttingAgentManager(): CuttingAgentManager {
        return cuttingAgentManager
    }

    fun getFileManager(): FileManager {
        return fileManager
    }
}
