package dev.edward.troll.manager.trolls;

import dev.edward.troll.manager.PlayerTroll;
import dev.edward.troll.manager.Troll;
import dev.edward.troll.util.ItemBuilder;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RespawnTroll implements Troll {

    @Override
    public String getName() {
        return ChatColor.GOLD + "Infinite Respawn";
    }

    @Override
    public String getRawName() {
        return "Infinite Respawn";
    }

    @Override
    public String getPermission() {
        return "troll.trolls.respawn";
    }

    @Override
    public String[] getDescription() {
        return new String[] {ChatColor.LIGHT_PURPLE + "Show the player the respawn screen infinitely."};
    }

    @Override
    public void execute(PlayerTroll playerTroll) {
        Player target = Bukkit.getPlayer(playerTroll.getTarget());

        PlayerConnection playerConnection = ((CraftPlayer) target).getHandle().playerConnection;
        for (int i = -128; i < 128; i++)
            playerConnection.sendPacket(new PacketPlayOutEntityStatus(((CraftPlayer) target).getHandle(), (byte) i));
    }

    @Override
    public ItemStack getItemStack() {
        return new ItemBuilder(Material.INK_SACK).setName(getName()).setLore(getDescription()).toItemStack();
    }
}
