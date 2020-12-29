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
        if (count % ChangeItems.instance.changeTime == 0) {
            for (player in Bukkit.getOnlinePlayers()) {
                ChangeItems.instance.changeItem(player)
            }
        }
        count++
    }

    private fun setProgress() {
        val prog:Double = count.toDouble() / ChangeItems.instance.changeTime
        val intPart = prog.toInt()
        bossBar.progress = 1.0 - (prog - intPart)
    }
}