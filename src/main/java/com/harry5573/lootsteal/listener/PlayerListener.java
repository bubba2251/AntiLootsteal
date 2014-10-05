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
package com.harry5573.lootsteal.listener;

import com.harry5573.lootsteal.AntiLootstealPlugin;
import com.harry5573.lootsteal.util.MessageUtil;
import com.harry5573.lootsteal.util.TaskUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

/**
 *
 * @author Harry5573
 */
public class PlayerListener implements Listener {

    static AntiLootstealPlugin plugin = AntiLootstealPlugin.get();

    @EventHandler(priority = EventPriority.HIGH)
    public void onLootstealDeath(PlayerDeathEvent e) {
        final Player killer = e.getEntity().getKiller();
        final Player killed = e.getEntity().getPlayer();

        if (killer == null) {
            return;
        }

        for (ItemStack stack : e.getDrops()) {
            Entity entity = killed.getWorld().dropItemNaturally(killed.getLocation(), stack);
            String time = String.valueOf(System.currentTimeMillis());
            entity.setMetadata("AntiLoot", new FixedMetadataValue(plugin, killer.getName() + " " + time));
        }

        e.getDrops().clear();
        TaskUtil.startKillerMessageTimer(killer, killed);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onLootstealPickup(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        int configtime = plugin.getConfig().getInt("timeinseconds") * 1000;

        if (Boolean.valueOf(e.getItem().hasMetadata("AntiLoot")).booleanValue()) {
            String getvalue = ((MetadataValue) e.getItem().getMetadata("AntiLoot").get(0)).asString();

            String[] theValue = getvalue.split(" ");

            String killersName = theValue[0];
            Long oldTime = Long.valueOf(theValue[1]);

            if (p.getName().equals(killersName)) {
                return;
            }

            if (System.currentTimeMillis() - oldTime.longValue() > configtime) {
                return;
            }

            e.setCancelled(true);

            if (!plugin.spamProt.contains(p)) {
                p.sendMessage(MessageUtil.translateToColorCode(plugin.getConfig().getString("message")).replaceAll("KILLER", killersName));
                TaskUtil.antiSpam(p);
            }
        }
    }
}
