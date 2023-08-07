package dev.edward.troll.manager.trolls;

import dev.edward.troll.manager.PlayerTroll;
import dev.edward.troll.manager.Troll;
import dev.edward.troll.util.ItemBuilder;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ElderGuardianTroll implements Troll {

    @Override
    public String getName() {
        return "Elder Guardian";
    }

    @Override
    public String getPermission() {
        return "troll.trolls.elderguardian";
    }

    @Override
    public String getDescription() {
        return "Show the player the guardian effect on their screen.";
    }

    @Override
    public void execute(PlayerTroll playerTroll) {
        Player target = Bukkit.getPlayer(playerTroll.getTarget());
        PlayerConnection playerConnection = ((CraftPlayer) target).getHandle().playerConnection;

        playerConnection.sendPacket(new PacketPlayOutGameStateChange(10, 0));
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemBuilder(Material.WATER_BUCKET).setName(getName()).setLore(getDescription()).toItemStack();
    }
}
