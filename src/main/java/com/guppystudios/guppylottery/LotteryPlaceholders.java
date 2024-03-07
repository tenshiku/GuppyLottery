package com.guppystudios.guppylottery;

import org.bukkit.entity.Player;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class LotteryPlaceholders extends PlaceholderExpansion{
    private final GuppyLottery plugin;

    public LotteryPlaceholders(GuppyLottery plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "guppylottery";
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion(){
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest (Player player, String identifier){
        if (player == null){
            return "";

        }
        PlayerStatsManager statsManager = new PlayerStatsManager(player);

        switch (identifier) {
            case "winner":
                return statsManager.getWinner();
            case "buyamount":
                return String.valueOf(statsManager.getBuyAmount);
            case "totaltickets":
                return String.valueOf(statsManager.getTotalTickets);
            case "playertickets":
                return String.valueOf(statsManager.getPlayerTickets);
            case "player":
                return statsManager.getPlayer();
            case "donation":
                return String.valueOf(statsManager.getDonation);
            case "totalpot":
                return String.valueOf(statsManager.getTotalPot);
            case "totalpot":
                return String.valueOf(statsManager.getTotalPot);
            case "totalwins":
                return String.valueOf(statsManager.getTotalWins);
            case "moneylost":
                return String.valueOf(statsManager.getMoneyLost);
            case "moneywon":
                return String.valueOf(statsManager.getMoneyWon);
            case "entries":
                return String.valueOf(statsManager.getEntries);

            default:
                return null;
        }
    }
}
