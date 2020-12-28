package com.github.rain1208.changeitems

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class ChangeItemTask: BukkitRunnable() {
    val bossBar = Bukkit.createBossBar("アイテムが入れ替わるまで", BarColor.PURPLE, BarStyle.SOLID)

    var count = 0

    override fun run() {
        setProgress()
        if (count % 30 == 0) {
            for (player in Bukkit.getOnlinePlayers()) {
                player.inventory.contents = getRandomInventory()
                player.inventory.setArmorContents(getRandomArmor())
            }
        }
        count++
    }

    private fun setProgress() {
        val prog:Double = count.toDouble() / 30.0
        val intPart = prog.toInt()
        bossBar.progress = 1.0 - (prog - intPart)
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
        val item = ItemStack(Material.values()[Random().nextInt(Material.values().size)])
        item.amount = Random().nextInt(64)
        return item
    }

    fun stop() {
        bossBar.removeAll()
        if (!isCancelled) cancel()
    }
}