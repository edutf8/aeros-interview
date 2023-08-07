package dev.edward.troll.inventory;

import dev.edward.troll.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class PlayersInventory {

    public Inventory getPlayersInventory() {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Online Players");
        List<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        boolean pagination = Bukkit.getServer().getOnlinePlayers().size() > 54;

        String playerName;
        if (!pagination) {
            for (int i = 0; i < Bukkit.getServer().getOnlinePlayers().size(); i++) {
                playerName = players.get(i).getName();
                inventory.setItem(i, new ItemBuilder(Material.SKULL_ITEM, 1)
                        .setSkullOwner(playerName)
                        .setName(playerName)
                        .toItemStack());
            }
        }


        return inventory;
    }

}
