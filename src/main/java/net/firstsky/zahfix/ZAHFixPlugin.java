package net.firstsky.zahfix;

import org.bukkit.plugin.java.JavaPlugin;

public class ZAHFixPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ZAHFixListener(this), this);
        getLogger().info("ZAHFix active - synchronisation des achats corrigée.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ZAHFix désactivé.");
    }
}
