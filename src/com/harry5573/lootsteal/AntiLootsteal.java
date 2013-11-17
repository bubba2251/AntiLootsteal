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

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Harry5573
 */
public class AntiLootsteal extends JavaPlugin {

    public static AntiLootsteal plugin;
    /**
     * Needed classes
     */
    public AntiLootstealUtil util;
    
    
    @Override
    public void onEnable() {
        plugin = this;

        log("================================");
        log("Anti Lootsteal version " + getDescription().getVersion() + " by Harry5573 starting...");

        this.saveDefaultConfig();
        
        this.util = new AntiLootstealUtil(this);

        Bukkit.getServer().getPluginManager().registerEvents(new AntiLootstealListener(this), this);
        
        this.getCommand("lootsteal").setExecutor(new AntiLootstealCommand(this));
        log("================================");
    }

    @Override
    public void onDisable() {
        log("Anti lootsteal disabling");
    }

    public void log(String msg) {
        this.getLogger().info(msg);
    }
}
