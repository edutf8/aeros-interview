package dev.edward.troll.inventory;

import dev.edward.troll.manager.Troll;
import dev.edward.troll.manager.TrollManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;

public class TrollsInventory {

    private final TrollManager trollManager;

    public TrollsInventory(TrollManager trollManager) {
        this.trollManager = trollManager;
    }

    public Inventory getTrollsInventory() {
        Inventory inventory = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Trolls");

        for (Troll troll : trollManager.getTrollMap().values())
            inventory.addItem(troll.getItemStack());

        return inventory;
    }
}
