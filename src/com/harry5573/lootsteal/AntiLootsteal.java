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

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Harry5573
 */
public class AntiLootsteal extends JavaPlugin {

    public static AntiLootsteal plugin;
    
    
    @Override
    public void onEnable() {
        this.getLogger().info("================================");
        this.getLogger().info("Anti Lootsteal version " + this.getDescription().getVersion() + " By Harry5573 Loading...");
        plugin = this;
        this.getServer().getPluginManager().registerEvents(new AntiLootstealListener(this), this);
        this.getLogger().info("================================");
    }
    
    @Override
    public void onDisable() {
        
    }
   
} 
