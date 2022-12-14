package coins.Command;

import coins.Messages;
import coins.coins;
import coins.coinsH2;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import utils.Start;

import java.sql.Connection;

public class ecoReset {

    Connection connection = Start.getPlugin().getSQL();

    public static void resetPlayer(@Nullable Connection connection, OfflinePlayer player, CommandSender sender) {
        if (Messages.getStorage().equalsIgnoreCase("None")) {
            coinsH2.setCoins(player, Start.getPlugin().getConfig().getInt("Starter_Coins"));
        } else if (Messages.getStorage().equalsIgnoreCase("MySQL")) {
            coins.setCoins(connection, player.getName(), Start.getPlugin().getConfig().getInt("Starter_Coins"));
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Start.getPlugin().getMessages().getString("Reset_Coins").replace("%second_player%", player.getName()).replace("%default_coins%", Start.getPlugin().getConfig().getInt("Starter_Coins")+"")));

    }

    public static void checkUser(@Nullable Connection connection, OfflinePlayer player, CommandSender sender, String args[]) {
        if(player == null || player.getName() == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Start.getPlugin().getMessages().getString("Not_Player")));
        }else {
            if(Messages.getStorage().equalsIgnoreCase("MySQL")) {
                if(coins.playerexist(connection, player.getUniqueId()) && player != null) {
                    resetPlayer(connection, player, sender);
                }else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Start.getPlugin().getMessages().getString("Not_Player")));
                }
            }else if(Messages.getStorage().equalsIgnoreCase("None")) {
                if(coinsH2.playerExist(player)) {
                    resetPlayer(null, player, sender);
                }else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Start.getPlugin().getMessages().getString("Not_Player")));
                }

            }
        }
    }

}
