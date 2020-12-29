package com.github.rain1208.changeitems

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

object ChangeTime: CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return true
        if (args[0].isEmpty()) return true

        if (args[0].toIntOrNull() != null) {
            ChangeItems.instance.changeTime = args[0].toInt()
        }

        return true
    }
}