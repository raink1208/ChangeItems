package com.github.rain1208.changeitems

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.java.JavaPlugin

class ChangeItems : JavaPlugin(),Listener {
    companion object {
        lateinit var instance: ChangeItems
        private set
    }

    val task = ChangeItemTask()

    override fun onEnable() {
        instance = this

        getCommand("start")?.setExecutor(StartCommand)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) =
        task.bossBar.addPlayer(event.player)


    @EventHandler
    fun onLeave(event: PlayerQuitEvent) =
        task.bossBar.removePlayer(event.player)


    fun start() {
        task.runTaskTimer(this, 0, 20)
        for (player in Bukkit.getOnlinePlayers()) {
            task.bossBar.addPlayer(player)
        }
        task.bossBar.isVisible = true
        server.pluginManager.registerEvents(this,this)
    }


    override fun onDisable() {
        task.bossBar.removeAll()
    }
}