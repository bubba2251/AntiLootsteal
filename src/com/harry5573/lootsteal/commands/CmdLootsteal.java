/*Copyright (C) Harry5573 2013-14

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.*/
package com.harry5573.lootsteal.commands;

import com.harry5573.lootsteal.AntiLootstealPlugin;
import com.harry5573.lootsteal.util.MessageUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Harry5573
 */
public class CmdLootsteal implements CommandExecutor {

    static AntiLootstealPlugin plugin = AntiLootstealPlugin.get();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player p = null;

        if (sender instanceof Player) {
            p = (Player) sender;
        }

        if (commandLabel.equalsIgnoreCase("lootsteal")) {

            if (p == null) {
                plugin.log("That is not a console command");
                return true;
            }

            if (!p.hasPermission("lootsteal.admin")) {
                p.sendMessage(MessageUtil.translateToColorCode(plugin.getConfig().getString("prefix")) + ChatColor.RED + " Permission denied!");
                return true;
            }

            if (args.length != 1) {
                p.sendMessage(MessageUtil.translateToColorCode(plugin.getConfig().getString("prefix")) + ChatColor.RED + " Usage: /lootsteal <reload>");
                return true;
            }

            if (args.length == 1) {

                if (args[0].equalsIgnoreCase("reload")) {
                    p.sendMessage(MessageUtil.translateToColorCode(plugin.getConfig().getString("prefix")) + ChatColor.GREEN + " Configuration Has Been Reloaded!");
                    plugin.reloadConfig();
                    return true;
                }

                p.sendMessage(MessageUtil.translateToColorCode(plugin.getConfig().getString("prefix")) + ChatColor.RED + " Usage: /lootsteal <reload>");
            }
        }
        return false;
    }
}
