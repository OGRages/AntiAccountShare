package me.rages.antiaccountshare.menu;

import me.rages.antiaccountshare.AntiAccountShare;
import me.rages.antiaccountshare.util.Util;
import me.rages.antiaccountshare.util.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LoginConfirmMenu implements InventoryHolder {

    public final static Set<Integer> ACCEPT_BUTTONS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21));
    public final static Set<Integer> CENTER_BUTTONS = new HashSet<>(Arrays.asList(4, 13, 22));
    public final static Set<Integer> DENY_BUTTONS = new HashSet<>(Arrays.asList(5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26));

    private static final int ROW_SIZE = 9;

    private Inventory inventory;

    public LoginConfirmMenu(AntiAccountShare plugin, String string, int rows) {
        this.inventory = Bukkit.createInventory(this, rows * ROW_SIZE, string);

        ItemStack accept = XMaterial.valueOf(plugin.getConfig().getString("accept-button.item").toUpperCase()).parseItem();
        ItemStack deny = XMaterial.valueOf(plugin.getConfig().getString("deny-button.item").toUpperCase()).parseItem();
        ItemStack blank = XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();

        Util.setNameAndLore(accept, plugin.getConfig().getString("accept-button.name"), plugin.getConfig().getStringList("accept-button.lore"));
        Util.setNameAndLore(deny, plugin.getConfig().getString("deny-button.name"), plugin.getConfig().getStringList("deny-button.lore"));
        Util.setNameAndLore(blank, " ", new ArrayList<>());


        ACCEPT_BUTTONS.forEach(slot -> getInventory().setItem(slot, accept));
        CENTER_BUTTONS.forEach(slot -> getInventory().setItem(slot, blank));
        DENY_BUTTONS.forEach(slot -> getInventory().setItem(slot, deny));

    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}
