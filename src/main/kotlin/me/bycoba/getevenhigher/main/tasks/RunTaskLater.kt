package me.bycoba.getevenhigher.main.tasks

import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

class RunTaskLater {

    companion object{

        fun scheduleTask(plugin: Plugin, delay: Long, task: Runnable) {
            Bukkit.getScheduler().runTaskLater(plugin, task, delay)
        }

        fun scheduleAsyncTask(plugin: Plugin, delay: Long, task: Runnable)  {
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay)
        }

    }
}