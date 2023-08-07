package dev.edward.troll.manager.trolls;

import dev.edward.troll.manager.PlayerTroll;
import dev.edward.troll.manager.Troll;
import dev.edward.troll.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MultiplyTroll implements Troll, Listener {

    private final Map<UUID, Long> multiplyTrollMap = new HashMap<>();

    @Override
    public String getName() {
        return "Multiply Mammals";
    }

    @Override
    public String getPermission() {
        return "troll.trolls.multiply";
    }

    @Override
    public String getDescription() {
        return "Kill one mob or animal and 2 more shall take it's place.\nExpires after 2 minutes.";
    }

    @Override
    public void execute(PlayerTroll playerTroll) {
        multiplyTrollMap.put(playerTroll.getTarget(), System.currentTimeMillis());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent entityDeathEvent) {
        if (!multiplyTrollMap.containsKey(entityDeathEvent.getEntity().getUniqueId())) return;

        if (entityDeathEvent.getEntityType() == EntityType.PLAYER) return;

        if (System.currentTimeMillis() - multiplyTrollMap.get(entityDeathEvent.getEntity().getUniqueId()) > 120000) {
            multiplyTrollMap.remove(entityDeathEvent.getEntity().getUniqueId());
            return;
        }

        entityDeathEvent.getEntity().getWorld().spawnEntity(entityDeathEvent.getEntity().getLocation(), entityDeathEvent.getEntityType());
        entityDeathEvent.getEntity().getWorld().spawnEntity(entityDeathEvent.getEntity().getLocation(), entityDeathEvent.getEntityType());
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemBuilder(Material.MOB_SPAWNER)
                .setName(getName())
                .setLore(getDescription())
                .toItemStack();
    }
}
