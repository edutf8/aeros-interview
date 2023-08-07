package dev.edward.troll.manager.trolls;

import dev.edward.troll.manager.PlayerTroll;
import dev.edward.troll.manager.Troll;
import dev.edward.troll.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EmptyInventoryTroll implements Troll, Listener {

    private final List<UUID> emptyTrollList = new ArrayList<>();

    @Override
    public String getName() {
        return "Drop One Drop Inventory";
    }

    @Override
    public String getPermission() {
        return "troll.trolls.emptyinventory";
    }

    @Override
    public String getDescription() {
        return "When a player drops an item, drop their entire inventory.\nOnly occurs once.";
    }

    @Override
    public void execute(PlayerTroll playerTroll) {
        emptyTrollList.add(playerTroll.getTarget());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent playerDropItemEvent) {
        if (!emptyTrollList.contains(playerDropItemEvent.getPlayer().getUniqueId())) return;

        Player player = playerDropItemEvent.getPlayer();

        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack == null) continue;
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        }

        for (ItemStack itemStack : player.getInventory().getArmorContents()) {
            if (itemStack == null) continue;
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
        }

        emptyTrollList.remove(player.getUniqueId());
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemBuilder(Material.DIAMOND_HELMET).setName(getName()).setLore(getDescription()).toItemStack();
    }
}
