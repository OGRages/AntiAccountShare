package me.rages.antiaccountshare.player;

import me.rages.antiaccountshare.AntiAccountShare;
import me.rages.antiaccountshare.menu.LoginConfirmMenu;
import me.rages.antiaccountshare.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListeners implements Listener {

    private AntiAccountShare plugin;

    public PlayerListeners(AntiAccountShare plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // open inventory
        openInventory(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory().getHolder() instanceof LoginConfirmMenu) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (LoginConfirmMenu.ACCEPT_BUTTONS.contains(event.getSlot())) {
                plugin.addVerifiedUser(player);
            } else if (LoginConfirmMenu.DENY_BUTTONS.contains(event.getSlot())) {
                player.kickPlayer(Util.color(plugin.getConfig().getString("kick-message")));
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof LoginConfirmMenu) {
            openInventory((Player) event.getPlayer());
        }
    }

    private void openInventory(Player player) {
        if (!plugin.getVerifiedUsers().containsKey(player.getUniqueId())) {
            plugin.getServer().getScheduler().runTaskLater(plugin, () -> player.openInventory(plugin.getLoginMenu().getInventory()), 10L);
        }
    }

}
