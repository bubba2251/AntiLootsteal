/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.harry5573.lootsteal.util;

import org.bukkit.ChatColor;

/**
 *
 * @author devan_000
 */
public class MessageUtil {

    /**
     * Translates a message to its colour code
     *
     * @param msg
     * @return *
     */
    public static String translateToColorCode(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
