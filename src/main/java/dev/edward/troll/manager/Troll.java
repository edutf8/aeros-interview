package dev.edward.troll.manager;

import org.bukkit.inventory.ItemStack;

public interface Troll {

    String getName();

    String getRawName();

    String getPermission();

    String[] getDescription();

    void execute(PlayerTroll playerTroll);

    ItemStack getItemStack();



}
