package me.rages.antiaccountshare.util;

import com.sun.tools.javac.jvm.Items;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static ItemStack setName(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(color(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(color(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static void setNameAndLore(ItemStack itemStack, String name, List<String> lore) {
        itemStack = setLore(setName(itemStack, name), lore);
    }

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> color(List<String> string) {
        List<String> colored = new ArrayList<>();
        for (String line : string) {
            colored.add(color(line));
        }
        return colored;
    }

}
