package me.lightning101.plugins.paperheal;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Paperheal extends JavaPlugin implements Listener {
	boolean blockNextHeal = false;
	
  /*  public void onDisable() {
        // TODO: Place any custom disable code here.
    } */

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        
    }

    
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){
    	if(!this.blockNextHeal){
	        Player p = event.getPlayer();
	        ItemStack pItemInHand = p.getItemInHand();
	        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
		        if(pItemInHand.getType() == Material.PAPER ) {
		        	event.setCancelled(true);
		        	Damageable dP = (Damageable) p;
		            double pMaxHealth = dP.getMaxHealth();
		            double pCurrentHealth = dP.getHealth();
		            if(pMaxHealth != pCurrentHealth) {
		            	if( (pMaxHealth - pCurrentHealth) < 4) {
		            		p.setHealth(pMaxHealth);
		            	} else {
		            		p.setHealth(pCurrentHealth + 4);
		            	}
		            	if(pItemInHand.getAmount() > 1){
		            		pItemInHand.setAmount(pItemInHand.getAmount() - 1);
		            	} else {
		            		p.getInventory().remove(pItemInHand);
		            	}
		                p.sendMessage(ChatColor.YELLOW + "You have healed yourself!");
		    		} else {
		    			p.sendMessage(ChatColor.YELLOW + "You already have full health!");
		    		}
		        }
           }
       } else {
    	   this.blockNextHeal = false;
       }
    }
    
    @EventHandler
    public void onPlayerClick(PlayerInteractEntityEvent event){
      Entity cE = event.getRightClicked();
      if(cE instanceof Player) {
    	Player cP = (Player) event.getRightClicked();
        Player p = event.getPlayer();
        if(cP instanceof Player){
        	ItemStack pItemInHand = p.getItemInHand();
        	if(pItemInHand.getType() == Material.PAPER ) {
        		Damageable dcP = (Damageable) cP;
                double cpMaxHealth = dcP.getMaxHealth();
                double cpCurrentHealth = dcP.getHealth();
        		if(cpMaxHealth != cpCurrentHealth) {
                	if( (cpMaxHealth - cpCurrentHealth) < 4) {
                		cP.setHealth(cpMaxHealth);
                	} else {
                		cP.setHealth(cpCurrentHealth + 4);
                	}
                	if(pItemInHand.getAmount() > 1){
                		pItemInHand.setAmount(pItemInHand.getAmount() - 1);
                		this.blockNextHeal = true;
                	} else {
                		p.getInventory().remove(pItemInHand);
                	}
                	p.sendMessage(ChatColor.YELLOW + cP.getName() + " has been healed!");
        		} else {
        			p.sendMessage(ChatColor.YELLOW + cP.getName() + " already has full health!");
        		}
            }
        }
      }
    }
}

