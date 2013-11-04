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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Harry5573
 */
public class AntiLootstealListener implements Listener {
    
    public static AntiLootsteal plugin;
    
    public AntiLootstealListener(AntiLootsteal instance) {
        this.plugin = instance;
    }
    

    public void removeItemAndLorePlayer(String player, ItemStack i, String time) {
        ItemMeta i1 = i.getItemMeta();
        List<String> lore1 = i1.getLore();
        lore1.remove("Killed Item");
        lore1.remove("Killer: " + player);
        lore1.remove("Time: " + time);
        i1.setLore(lore1);
        i.setItemMeta(i1);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLootstealDeath(PlayerDeathEvent e) {
        final Player killer = e.getEntity().getKiller();
        final Player killed = e.getEntity();
        
        if (killer == null) {
            return;
        }

        for (ItemStack i : e.getDrops()) {
            ItemMeta i1 = i.getItemMeta();

            if (i1.hasLore()) {
                List<String> lore = i1.getLore();
                
                lore.add("Killed Item");
                lore.add("Killer: " + killer.getName());
                i1.setLore(lore);
                i.setItemMeta(i1);
            } else if (!i1.hasLore()) {
                List<String> lore1 = new ArrayList<>();
                
                lore1.add("Time: " + System.currentTimeMillis());
                lore1.add("Killed Item");
                lore1.add("Killer: " + killer.getName());
                i1.setLore(lore1);
                i.setItemMeta(i1);
            }
        }

        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                String newmsg = plugin.getConfig().getString("messagefree").replaceAll("(&([a-f0-9]))", "\u00A7$2").replaceAll("PLAYER", killed.getName());
                killer.sendMessage(newmsg);
            }
        }, plugin.getConfig().getInt("timeinseconds") * 20);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLootstealPickup(PlayerPickupItemEvent e) {
        final Player p = e.getPlayer();

        ItemStack item = e.getItem().getItemStack();
        ItemMeta meta = item.getItemMeta();

        if (!meta.hasLore()) {
            return;
        }

        List<String> lore = meta.getLore();

        if (!lore.contains("Killed Item")) {
            return;
        }

        long time = Long.valueOf(this.getValueFromLore(lore, "Time:"));
        long timenew = time + TimeUnit.SECONDS.toMillis(plugin.getConfig().getInt("timeinseconds"));

        String player = getValueFromLore(lore, "Killer:");

        if (System.currentTimeMillis() >= timenew) {
            this.removeItemAndLorePlayer(player, item, String.valueOf(time));
            return;
        }

        if (lore.contains("Killer: " + p.getName())) {
            this.removeItemAndLorePlayer(p.getName(), item, String.valueOf(time));
            return;
        }
        
        e.setCancelled(true);

        p.sendMessage(plugin.getConfig().getString("message").replaceAll("(&([a-f0-9]))", "\u00A7$2"));
    }

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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onHopperPickup(InventoryPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();

        ItemMeta meta = item.getItemMeta();
        
        if (!meta.hasLore()) {
            return;
        }
        
        List<String> lore = meta.getLore();
        
        if (!lore.contains("Killed Item")) {
            return;
        }
        long time = Long.valueOf(this.getValueFromLore(lore, "Time:"));
        String player = getValueFromLore(lore, "Killer:");
        
        this.removeItemAndLorePlayer(player, item, String.valueOf(time));
    }
}
