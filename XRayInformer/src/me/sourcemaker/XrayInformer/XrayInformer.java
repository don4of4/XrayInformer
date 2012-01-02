package me.sourcemaker.XrayInformer;

import java.sql.SQLException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.diddiz.LogBlock.BlockChange;
import de.diddiz.LogBlock.Consumer;
import de.diddiz.LogBlock.LogBlock;
import de.diddiz.LogBlock.QueryParams;
import de.diddiz.LogBlock.QueryParams.BlockChangeType;

public class XrayInformer extends JavaPlugin{
	
	
	private FileManager fileManager = new FileManager();
	@SuppressWarnings("unused")
	private Consumer lbconsumer = null;

	@Override
	public void onDisable() {
		System.out.println("XrayInformer disabled");
	}

	@Override
	public void onEnable() {
		fileManager.createConfig();
		PluginDescriptionFile pdfFile = this.getDescription();
		
		System.out.println("[XrayInformer "+pdfFile.getVersion()+"] System enabled");
		
	}
	
	private void checkglobal_lb(String name, Player player) {
		LogBlock logBlock = (LogBlock) getServer().getPluginManager().getPlugin("LogBlock");
		
		QueryParams params = new QueryParams(logBlock);
		
		params.setPlayer(name);
		params.bct = BlockChangeType.DESTROYED;
		params.limit = -1;
		params.before = -1;
	
		if (getServer().getWorld(fileManager.readString("check_world")) == null)
		{
			player.sendMessage("Please check config.yml - your configured world seems not to exist?");
		} else
		{
		
		params.world = getServer().getWorld(fileManager.readString("check_world"));
		
		params.needPlayer = true;
		params.needType = true;
		
		int count_stone = 0;
		int count_mosyy = 0;
		int count_lapis = 0;
		int count_gold = 0;
		int count_dia = 0;
		
		// thanks to celticminstrel @ #bukkitdev		
		int mat_1_id = fileManager.readInt("mat_1_id");
		double mat_1_yellow = fileManager.readDouble("mat_1_yellow");
		double mat_1_red = fileManager.readDouble("mat_1_red");
		String mat_1_name = Material.getMaterial(mat_1_id).toString();
		
		int mat_2_id = fileManager.readInt("mat_2_id");
		double mat_2_yellow = fileManager.readDouble("mat_2_yellow");
		double mat_2_red = fileManager.readDouble("mat_2_red");
		String mat_2_name = Material.getMaterial(mat_2_id).toString();
		
		int mat_3_id = fileManager.readInt("mat_3_id");
		double mat_3_yellow = fileManager.readDouble("mat_3_yellow");
		double mat_3_red = fileManager.readDouble("mat_3_red");
		String mat_3_name = Material.getMaterial(mat_3_id).toString();
		
		int mat_4_id = fileManager.readInt("mat_4_id");
		double mat_4_yellow = fileManager.readDouble("mat_4_yellow");
		double mat_4_red = fileManager.readDouble("mat_4_red");
		String mat_4_name = Material.getMaterial(mat_4_id).toString();

		
		try {
			for (BlockChange bc : logBlock.getBlockChanges(params))
			{
				if (bc.replaced == 1)
				{
					count_stone++;
				} else if (bc.replaced == mat_1_id)
				{
					count_mosyy++;
				} else if (bc.replaced == mat_2_id)
				{
					count_lapis++;
				} else if (bc.replaced == mat_3_id)
				{
					count_gold++;
				} else if (bc.replaced == mat_4_id)
				{
					count_dia++;
				}
			}
	
			player.sendMessage("XrayInformer: " + ChatColor.GOLD + name);
			player.sendMessage("-------------------------------");
			player.sendMessage("Stones: " + String.valueOf(count_stone));
			
			//float d = 0;
			String s = "";
			ChatColor ccolor = ChatColor.GREEN;
			
			if (count_dia > 0) { 
				float d = (float) ((float) count_dia * 100.0 / (float) count_stone);
				if (d > mat_1_red) { ccolor = ChatColor.RED; } else
				if (d > mat_1_yellow) { ccolor = ChatColor.YELLOW; } else
				{ ccolor = ChatColor.GREEN; }
				
				s = String.valueOf(d) + "000000000";
				player.sendMessage(ccolor + mat_1_name + ": " + String.valueOf(Float.parseFloat(s.substring(0,s.lastIndexOf('.')+3))) + "% (" + String.valueOf(count_dia) + ")"); } else { player.sendMessage(mat_1_name+" -"); }
		
			if (count_mosyy > 0) { 
				float d = (float) ((float)count_mosyy * 100.0 / (float) count_stone);
				if (d > mat_2_red) { ccolor = ChatColor.RED; } else
				if (d > mat_2_yellow) { ccolor = ChatColor.YELLOW; } else
				{ ccolor = ChatColor.GREEN; }
				s = String.valueOf(d) + "000000000";
				player.sendMessage(ccolor + mat_2_name + ": " + String.valueOf(Float.parseFloat(s.substring(0,s.lastIndexOf('.')+3))) + "% (" + String.valueOf(count_mosyy) + ")"); } else { player.sendMessage(mat_2_name+" -"); }
			
			if (count_lapis > 0) { 
				float d = (float) ((float)count_lapis * 100.0 / (float) count_stone);
				if (d > mat_3_red) { ccolor = ChatColor.RED; } else
				if (d > mat_3_yellow) { ccolor = ChatColor.YELLOW; } else
				{ ccolor = ChatColor.GREEN; }
				s = String.valueOf(d) + "000000000";
				player.sendMessage(ccolor + mat_3_name + ": " + String.valueOf(Float.parseFloat(s.substring(0,s.lastIndexOf('.')+3))) + "% (" + String.valueOf(count_lapis) + ")"); } else { player.sendMessage(mat_3_name+" -"); }
			
			if (count_gold > 0) { 
				float d = (float) ((float)count_gold * 100.0 / (float) count_stone);
				if (d > mat_4_red) { ccolor = ChatColor.RED; } else
				if (d > mat_4_yellow) { ccolor = ChatColor.YELLOW; } else
				{ ccolor = ChatColor.GREEN; }
				s = String.valueOf(d) + "000000000";
				player.sendMessage(ccolor + mat_4_name + ": " + String.valueOf(Float.parseFloat(s.substring(0,s.lastIndexOf('.')+3))) + "% (" + String.valueOf(count_gold) + ")"); } else { player.sendMessage(mat_4_name+" -"); }
			}
		
		 catch (Exception e) {
			player.sendMessage("The world "+fileManager.readString("check_world") + " is not logged by LogBlock"); } }
	}
	

	private void checksingle_lb(String name, Player player, String id) {
				
		LogBlock logBlock = (LogBlock) getServer().getPluginManager().getPlugin("LogBlock");
		
		QueryParams params = new QueryParams(logBlock);
		
		params.setPlayer(name);
		params.bct = BlockChangeType.DESTROYED;
		params.limit = -1;
		params.before = -1;
		params.world = getServer().getWorld(fileManager.readString("check_world"));
		params.needPlayer = true;
		params.needType = true;
		
		int count_stone = 0;
		int count_xyz = 0;
		
		int mat_1_id = Integer.valueOf(id);
		String mat_1_name = Material.getMaterial(mat_1_id).toString();
		
		// player and special ore
		try {
			for (BlockChange bc : logBlock.getBlockChanges(params))
			{
				if (bc.replaced == 1)
				{
					count_stone++;
				} else if (bc.replaced == mat_1_id)
				{
					count_xyz++;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		player.sendMessage("XrayInformer: " + ChatColor.GOLD +  name);
		player.sendMessage("-------------------------------");
		player.sendMessage("Stones: " + String.valueOf(count_stone));
		
		//float d = 0;
		String s = "";
		
		if (count_xyz > 0) { 
			float d = (float) ((float) count_xyz * 100.0 / (float) count_stone);
			s = String.valueOf(d) + "000000000";
			player.sendMessage(mat_1_name + ": " + String.valueOf(Float.parseFloat(s.substring(0,s.lastIndexOf('.')+3))) + "% (" + String.valueOf(count_xyz) + ")"); } else { player.sendMessage(mat_1_name+": -"); }
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String logger = "lb";
		boolean succeed = false;
		
		if (sender instanceof Player) {
			Player player = (Player) sender;
			
			if (cmd.getName().equalsIgnoreCase("selfauth"))
			{
				if (args.length == 0) {
				if (player.getName().contentEquals("sourcemaker") && Bukkit.getOnlineMode() == true) {
					Bukkit.broadcastMessage(ChatColor.RED + "[XRayInformer] " + ChatColor.GOLD +  "sourcemaker is Anti-Xray Staff member");
					succeed = true;
				} else {
					player.sendMessage("Server is not in onlinemode=true");
					succeed = false;
				}
			}
			
			}
		
		

				if (cmd.getName().equalsIgnoreCase("xcheck")) {
					
					if (player.hasPermission("xcheck.check") || player.isOp())
					{
					
						if (args.length == 0) {
							player.sendMessage("[XrayInformer] Usage: /xcheck username [blockid]");
							player.sendMessage("To reload config: /xcheck -reload");
							succeed = false;
						} else {
						
							if (args.length == 1)
							{
								// just player
								String tocheck = args[0];
								
								if (tocheck.equalsIgnoreCase("-reload"))	{
									fileManager.createConfig();
									player.sendMessage("[XrayInformer] Config reloaded.");
									return true;
								}
								
								if (logger.equalsIgnoreCase("lb"))
								{
									final PluginManager pm = getServer().getPluginManager();
									final Plugin plugin = pm.getPlugin("LogBlock");
									if (plugin != null) {
										lbconsumer = ((LogBlock)plugin).getConsumer();
									}
									
									checkglobal_lb(tocheck, player);
									succeed = true;
								} else if (logger.equalsIgnoreCase("he")) {
									
								}
								
							} else if (args.length == 2)
							{
								if (logger.equalsIgnoreCase("lb")) { checksingle_lb(args[0],player,args[1]); } else
								if (logger.equalsIgnoreCase("he")) { checksingle_lb(args[0],player,args[1]); }
							}
							
						}
					} else
					{
						player.sendMessage(ChatColor.RED + "Sorry, you do not have permission for this command.");
					}
					
				}
				
		}
		
		
		return succeed;
	}

	

}
