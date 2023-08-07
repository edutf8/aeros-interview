package dev.edward.troll.manager.trolls;

import dev.edward.troll.manager.PlayerTroll;
import dev.edward.troll.manager.Troll;
import dev.edward.troll.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SmiteTroll implements Troll, Listener {

    private final List<UUID> smiteList = new ArrayList<>();

    @Override
    public String getName() {
        return ChatColor.GOLD + "Smite";
    }

    @Override
    public String getRawName() {
        return "Smite";
    }

    @Override
    public String getPermission() {
        return "troll.trolls.smite";
    }

    @Override
    public String[] getDescription() {
        return new String[] {ChatColor.LIGHT_PURPLE + "Strike lightning on your target."};
    }

    @Override
    public void execute(PlayerTroll playerTroll) {
        Player target = Bukkit.getPlayer(playerTroll.getTarget());
        if (target == null) {
            Bukkit.getPlayer(playerTroll.getTroller()).sendMessage(ChatColor.RED + "Player not found.");
            return;
        }

        smiteList.add(target.getUniqueId());
        target.getWorld().strikeLightning(target.getLocation());
    }

    @EventHandler
    public void onDamage(EntityDamageEvent entityDamageEvent) {
        if (!(entityDamageEvent.getEntity() instanceof Player)) return;

        Player player = (Player) entityDamageEvent.getEntity();
        if (!smiteList.contains(player.getUniqueId())) return;

        if (entityDamageEvent.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            entityDamageEvent.setCancelled(true);
            smiteList.remove(player.getUniqueId());
        }
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemBuilder(Material.BLAZE_ROD)
                .setName(getName())
                .setLore(getDescription())
                .toItemStack();
    }
}
