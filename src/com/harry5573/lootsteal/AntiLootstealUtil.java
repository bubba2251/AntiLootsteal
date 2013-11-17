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
     * Returns a value from an items lore
     *
     * @param par1
     * @param par2
     * @return
     */
    public String getValueFromLore(List<String> par1, String par2) {
        String retString = "";
        try {
            if (par1 != null) {
                for (int i = 0; i < par1.size(); i++) {
                    if (par1.get(i).contains(par2)) {
                        retString = cleanUpLore(par1.get(i));
                        return retString;
                    }
                }
            } else {
                return retString;
            }
        } catch (Exception e) {
        }
        return retString;
    }

    private static String cleanUpLore(String par1) {
        String[] arg = par1.split(":");
        arg[1] = ChatColor.stripColor(arg[1]);
        String str = arg[1].replace("%", "").trim().toString();
        return str;
    }
    
    /**
     * Cleans the item lore from our methods
     *
     * @param player
     * @param i
     * @param time
     */
    public void cleanItemLore(String player, ItemStack i, String time) {
        ItemMeta i1 = i.getItemMeta();
        List<String> lore1 = i1.getLore();
        lore1.remove("Killed Item");
        lore1.remove("Killer: " + player);
        lore1.remove("Time: " + time);
        i1.setLore(lore1);
        i.setItemMeta(i1);
    }
}
