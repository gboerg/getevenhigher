package me.bycoba.getevenhigher.main.tasks

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class TaskSpecific {

    companion object{

        fun scheduleDelayedTask(plugin: Plugin, delay: Long, task: Runnable) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, task, delay)
        }

        fun scheduleRepeatingTask(plugin: Plugin, delay: Long, duration: Long,   task: Runnable)  {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, delay, duration)
        }
    }
}