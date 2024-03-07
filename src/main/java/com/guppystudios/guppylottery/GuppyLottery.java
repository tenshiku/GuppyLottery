package com.guppystudios.guppylottery;

import org.bukkit.plugin.java.JavaPlugin;

public final class GuppyLottery extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")){
            new LotteryPlaceholders(this).register();
        } else {
            getLogger().warning("PlaceholderAPI not found. Some features may not work.");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("GuppyLottery has been disabled.");
    }
}
