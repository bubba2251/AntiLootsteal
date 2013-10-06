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
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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

    
    @EventHandler(priority= EventPriority.HIGH)
    public void onLootstealDeath(PlayerDeathEvent e) {
        final Player killer = e.getEntity().getKiller();
        
        if (killer == null) {
            return;
        }
        
        for (ItemStack i : e.getDrops()) {
            ItemMeta i1 = i.getItemMeta();
     
            if (i1.hasLore()) {
                List<String> lore = i1.getLore();
                
                lore.add("Killed Item");
                lore.add(killer.getName());
                i1.setLore(lore);
                i.setItemMeta(i1);
                
            } else if (i1.hasLore() == false) {
                List<String> lore1 = new ArrayList<String>();
                
                lore1.add("Killed Item");
                lore1.add(killer.getName());
                i1.setLore(lore1);
                i.setItemMeta(i1);
            }
        }

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onLootstealPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();

        ItemStack item = e.getItem().getItemStack();

        if (item.getItemMeta().hasLore() == false) {
            return;
        }

        ItemMeta blabla = item.getItemMeta();
        List<String> lore = blabla.getLore();
        
        if (!lore.contains("Killed Item")) {
            return;
        }

        if (lore.contains(p.getName())) {
            ItemMeta i1 = item.getItemMeta();
            List<String> lore1 = i1.getLore();
            lore1.remove("Killed Item");
            lore1.remove(p.getName());
            i1.setLore(lore1);
            item.setItemMeta(i1);
            return;
        }

        e.setCancelled(true);
        p.sendMessage(ChatColor.RED + "You cannot pick that up! Only the killer can");
    }
}
