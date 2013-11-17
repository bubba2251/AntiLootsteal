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
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
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

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLootstealDeath(PlayerDeathEvent e) {
        final Player killer = e.getEntity().getKiller();
        final Player killed = e.getEntity();
        List<String> lore = new ArrayList<>();

        if (killer != null) {
            for (ItemStack item : e.getDrops()) {
                ItemMeta meta = item.getItemMeta();

                if (meta.hasLore()) {
                    for (String s : meta.getLore()) {
                        lore.add(s);
                    }
                }

                    lore.add("Time: " + System.currentTimeMillis());
                    lore.add("Killed Item");
                    lore.add("Killer: " + killer.getName());
                    meta.setLore(lore);
                    item.setItemMeta(meta);
            }
            plugin.util.cooldown(killer, killed);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLootstealPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();

        ItemStack item = e.getItem().getItemStack();

        ItemMeta meta = item.getItemMeta();
        
        if (meta != null) {
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();

                if (lore.contains("Killed Item")) {

                    long time = Long.valueOf(plugin.util.getValueFromLore(lore, "Time:"));
                    long timenew = time + TimeUnit.SECONDS.toMillis(plugin.getConfig().getInt("timeinseconds"));
                    
                    String player = plugin.util.getValueFromLore(lore, "Killer:");

                    if (lore.contains("Killer: " + p.getName())) {
                        plugin.util.cleanItemLore(p.getName(), item, String.valueOf(time));
                        return;
                    }

                    if (System.currentTimeMillis() > timenew) {
                        plugin.util.cleanItemLore(player, item, String.valueOf(time));
                        return;
                    }
                    e.setCancelled(true);

                    if (!plugin.spamProt.contains(p)) {
                        p.sendMessage(plugin.util.translateToColorCode(plugin.getConfig().getString("message")).replaceAll("KILLER", player));
                        plugin.util.antiSpam(p);
                    }
                }
            }
        }
    }

    /**
     * If a hopper trys to pick it up
     *
     * @param e
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onHopperPickup(InventoryPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();

                if (lore.contains("Killed Item")) {
                    long time = Long.valueOf(plugin.util.getValueFromLore(lore, "Time:"));
                    String player = plugin.util.getValueFromLore(lore, "Killer:");

                    plugin.util.cleanItemLore(player, item, String.valueOf(time));
                }
            }
        }
    }
}
