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

import com.harry5573.lootsteal.commands.CmdLootsteal;
import com.harry5573.lootsteal.listener.PlayerListener;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Harry5573
 */
public class AntiLootstealPlugin extends JavaPlugin {

    public static AntiLootstealPlugin plugin;

    /**
     * AntiSpam
     */
    public List<Player> spamProt = new ArrayList<>();

    public static AntiLootstealPlugin get() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;

        log("================================");
        log("Anti Lootsteal version " + getDescription().getVersion() + " by Harry5573 starting...");

        this.saveDefaultConfig();

        registerEvents();

        registerCommands();
        log("================================");
    }

    @Override
    public void onDisable() {
        log("Anti lootsteal disabling");
    }

    public void log(String msg) {
        this.getLogger().info(msg);
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
    }

    private void registerCommands() {
        getCommand("lootsteal").setExecutor(new CmdLootsteal());
    }
}
