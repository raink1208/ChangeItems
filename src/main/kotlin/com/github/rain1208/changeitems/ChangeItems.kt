package com.github.rain1208.changeitems

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class ChangeItems : JavaPlugin(),Listener {
    companion object {
        lateinit var instance: ChangeItems
        private set
    }

    private val task = ChangeItemTask()
    var changeTime = 30

    private val items:MutableList<Material> = mutableListOf()

    private val removeList = mutableListOf(
        "COMMAND_BLOCK",
        "COMMAND_BLOCK_MINECART",
        "CHAIN_COMMAND_BLOCK",
        "REPEATING_COMMAND_BLOCK",
        "SPAWNER",
        "BEDROCK",
        "END_PORTAL_FRAME",
        "BARRIER"
    )

    init {
        for (material in Material.values()) {
            if (material.name.startsWith(Material.LEGACY_PREFIX)) continue
            if (material.name.endsWith("SPAWN_EGG")) continue
            if (material.name.endsWith("PORTAL")) continue
            if (removeList.contains(material.name)) continue

            items.add(material)
        }
    }

    override fun onEnable() {
        instance = this

        getCommand("start")?.setExecutor(StartCommand)
        getCommand("change")?.setExecutor(ChangeTime)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        task.bossBar.addPlayer(event.player)
        changeItem(event.player)
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent) =
        task.bossBar.removePlayer(event.player)

    @EventHandler
    fun onDrop(event: PlayerDropItemEvent) {
        event.itemDrop.remove()
    }

    fun start() {
        task.runTaskTimer(this, 0, 20)
        for (player in Bukkit.getOnlinePlayers()) {
            task.bossBar.addPlayer(player)
        }
        task.bossBar.isVisible = true
        server.pluginManager.registerEvents(this,this)
    }

    fun changeItem(player: Player) {
        player.inventory.itemInOffHand.type = Material.AIR
        for (i in 1..4) {
            player.openInventory.topInventory.setItem(i, ItemStack(Material.AIR))
        }
        player.openInventory.cursor = ItemStack(Material.AIR)
        player.inventory.contents = getRandomInventory()
        player.inventory.setArmorContents(getRandomArmor())
    }


    private fun getRandomArmor(): Array<ItemStack> {
        val contents = mutableListOf<ItemStack>()

        repeat(4) {
            contents.add(getRandomItem())
        }

        return contents.toTypedArray()
    }

    private fun getRandomInventory(): Array<ItemStack> {
        val contents = mutableListOf<ItemStack>()

        repeat(36) {
            contents.add(getRandomItem())
        }

        return contents.toTypedArray()
    }

    private fun getRandomItem(): ItemStack {
        val item = ItemStack(items[Random().nextInt(items.size)])
        item.amount = Random().nextInt(64)
        return item
    }

    override fun onDisable() {
        task.bossBar.removeAll()
    }
}