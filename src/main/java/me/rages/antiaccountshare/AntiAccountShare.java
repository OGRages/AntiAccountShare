package me.rages.antiaccountshare;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.rages.antiaccountshare.menu.LoginConfirmMenu;
import me.rages.antiaccountshare.player.PlayerData;
import me.rages.antiaccountshare.player.PlayerListeners;
import me.rages.antiaccountshare.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class AntiAccountShare extends JavaPlugin implements Runnable {

    private final LoginConfirmMenu loginMenu = new LoginConfirmMenu(this, Util.color(getConfig().getString("menu-title")), 3);

    private Gson gson = new GsonBuilder().setPrettyPrinting().enableComplexMapKeySerialization().disableHtmlEscaping().create();
    private Map<UUID, PlayerData> verifiedUsers = new HashMap<>();

    private static final int UPDATE_TIMER = 900 * 20;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, this, UPDATE_TIMER, UPDATE_TIMER);

        if (Files.isReadable(Paths.get(getDataFolder() + File.separator + "verified-users.json"))) {
            try (Reader reader = new FileReader(getDataFolder() + File.separator + "verified-users.json")) {
                this.verifiedUsers = new Gson().fromJson(reader, new TypeToken<Map<UUID, Long>>() {}.getType());
            } catch (IOException e) {
                getServer().getLogger().log(Level.SEVERE, "Failed to load user data!");
            }
        }
    }

    @Override
    public void onDisable() {
        try (FileWriter writer = new FileWriter(this.getDataFolder() + File.separator + "verified-users.json")) {
            gson.toJson(this.verifiedUsers, writer);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Failed to save user data!");
        }
    }

    @Override
    public void run() {
        // Every 30 minutes clear up unused data
        for (Map.Entry<UUID, PlayerData> users : getVerifiedUsers().entrySet()) {
            if (System.currentTimeMillis() > users.getValue().getVerifiedTime()) {
                getVerifiedUsers().remove(users.getKey());
            }
        }
    }

    public LoginConfirmMenu getLoginMenu() {
        return loginMenu;
    }

    public void addVerifiedUser(Player player) {
        PlayerData data = new PlayerData(System.currentTimeMillis() + (1000 * getConfig().getInt("verify-check-timer")), player.getAddress().getAddress().toString());
        this.verifiedUsers.put(player.getUniqueId(), data);
    }

    public Map<UUID, PlayerData> getVerifiedUsers() {
        return verifiedUsers;
    }
}
