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

package com.harry5573.lootsteal;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Harry5573
 */
public class AntiLootstealUtil {

    AntiLootsteal plugin;
    
    public AntiLootstealUtil(AntiLootsteal instance) {
        this.plugin = instance;
    }
    
    /**
     * Translates a message to its colour code
     *
     * @param msg
     * @return      *
     */
    public String translateToColorCode(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * Starts the cooldown for the player
     * @param killer
     * @param killed 
     */
    public void cooldown(final Player killer, final Player killed) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                killer.sendMessage(plugin.util.translateToColorCode(plugin.getConfig().getString("messagefree")).replaceAll("PLAYER", killed.getName()));
            }
        }, plugin.getConfig().getInt("timeinseconds") * 20);
    }

    /**
     * AntiSpam
     */
    public void antiSpam(final Player p) {
        plugin.spamProt.add(p);
        
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.spamProt.remove(p);
            }
        }, plugin.getConfig().getInt("timeantispam") * 20);
    }

    /**
     * Cleans the item lore from our methods
     *
     */
    public void cleanItemLore(ItemStack item, List<String> remove) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        for (String s : remove) {
            lore.remove(s);
        }

    }
}
