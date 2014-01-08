/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.harry5573.lootsteal.util;

import com.harry5573.lootsteal.AntiLootstealPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author devan_000
 */
public class TaskUtil {

    static AntiLootstealPlugin plugin = AntiLootstealPlugin.get();

    /**
     * Starts the killer message timer for the player
     *
     * @param killer
     * @param killed
     */
    public static void startKillerMessageTimer(final Player killer, final Player killed) {
        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                killer.sendMessage(MessageUtil.translateToColorCode(plugin.getConfig().getString("messagefree")).replaceAll("PLAYER", killed.getName()));
            }
        }, plugin.getConfig().getInt("timeinseconds") * 20);
    }

    /**
     * AntiSpam
     *
     * @param p
     */
    public static void antiSpam(final Player p) {
        plugin.spamProt.add(p);

        plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.spamProt.remove(p);
            }
        }, plugin.getConfig().getInt("timeantispam") * 20);
    }

}
