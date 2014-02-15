package de.lucawimmer.slsjoin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
	  Player player = event.getPlayer();
	  if(!player.hasPlayedBefore()) {
		  Boolean onjoin = main.config.getBoolean("onjoin");
		  String joinmessage = main.config.getString("joinmessage");
		  if(onjoin) {
			  Bukkit.broadcastMessage(replaceVars(joinmessage, player));
		  }
	  }
  }
  
  public String replaceVars(String s, Player p){
      String r = s;
      
      DateFormat dateFormat = new SimpleDateFormat("HH:mm");
      Date date = new Date();
      String datetime  = dateFormat.format(date).replace(":", "|");
      
      int countoffline = Bukkit.getOfflinePlayers().length;
  
      r = r.replace("{online}", Bukkit.getOnlinePlayers().length+"");
      r = r.replace("{max}", Bukkit.getMaxPlayers()+"");
      r = r.replace("{player}", p.getName());
      r = r.replace("{displayname}", p.getDisplayName());
      r = r.replace("{name}", p.getName());
      r = r.replace("{time}", datetime);
      r = r.replace("{counter}", Integer.toString(countoffline));
      r = r.replace("{heart}", "❤");
      r = r.replace(":", "|");

      r = ChatColor.translateAlternateColorCodes('&', r);
      return r;

  }
}
