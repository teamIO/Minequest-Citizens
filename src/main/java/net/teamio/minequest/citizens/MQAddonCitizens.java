/*
 * This file is part of MineQuest-Citizens, MineQuest connector to CitizensAPI.
 * MineQuest-Citizens is licensed under GNU General Public License v3.
 * Copyright (C) 2012 teamIO + Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.teamio.minequest.citizens;

import java.io.File;

import net.teamio.minequest.citizens.command.ContactsCommandFrontend;
import net.teamio.minequest.citizens.command.NPCCommandFrontend;
import net.teamio.minequest.citizens.listener.CitizensListener;
import net.teamio.minequest.citizens.listener.CommandListener;
import net.teamio.minequest.citizens.listener.EnterExitListener;
import net.teamio.minequest.citizens.statistic.NPCStatistic;
import net.teamio.minequest.citizens.tracker.DescriptionManager;

import org.bukkit.plugin.java.JavaPlugin;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Utils.PropertiesFile;

public class MQAddonCitizens extends JavaPlugin {
	
	public static MQAddonCitizens activePlugin = null;
	public static DescriptionManager descriptionManager = null;
	public static int firstNPC = -1;
	
	@Override
	public void onEnable(){
		Managers.log("============{ teamIO : MQCitizens }============");
		Managers.log("============{     now loading     }============");
		if (getServer().getPluginManager().getPlugin("MineQuest") == null) {
			getServer().getLogger().severe("============= teamIO : MQCitizens =============");
			getServer().getLogger().severe("MineQuest is required for MQCitizens to operate!");
			getServer().getLogger().severe("Please install MineQuest first!");
			getServer().getLogger().severe("You can find the latest version here:");
			getServer().getLogger().severe("http://dev.bukkit.org/server-mods/minequest/");
			getServer().getLogger().severe("==============================================");
			setEnabled(false);
			return;
		}
		if (getServer().getPluginManager().getPlugin("Citizens") == null) {
			getServer().getLogger().severe("============= teamIO : MQCitizens =============");
			getServer().getLogger().severe("Citizens is required for MQCitizens to operate!");
			getServer().getLogger().severe("Please install Citizens v2 first!");
			getServer().getLogger().severe("You can find the latest version here:");
			getServer().getLogger().severe("http://ci.citizensnpcs.net/");
			getServer().getLogger().severe("==============================================");
			setEnabled(false);
			return;
		}
		if (getServer().getPluginManager().getPlugin("Spout") == null) {
			getServer().getLogger().warning("============= teamIO : MQCitizens =============");
			getServer().getLogger().warning("Spout is optional for MQCitizens, but provides");
			getServer().getLogger().warning("additional features such as GUIs for players");
			getServer().getLogger().warning("using Spoutcraft! You can get SpoutPlugin and");
			getServer().getLogger().warning("Spoutcraft at http://get.spout.org/");
			getServer().getLogger().warning("==============================================");
		}
		activePlugin = this;
		descriptionManager = new DescriptionManager();
		getCommand("mqnpc").setExecutor(new NPCCommandFrontend());
		getCommand("contacts").setExecutor(new ContactsCommandFrontend());
		Managers.getStatisticManager().registerStatistic(NPCStatistic.class);
		getServer().getPluginManager().registerEvents(new CitizensListener(), this);
		getServer().getPluginManager().registerEvents(new CommandListener(), this);
		getServer().getPluginManager().registerEvents(new EnterExitListener(), this);
		PropertiesFile f = new PropertiesFile(Managers.getActivePlugin().getDataFolder().getAbsolutePath() + File.separator + "npc.properties");
		firstNPC = f.getInt("defaultnpc", -1);
	}
	
	@Override
	public void onDisable(){
		descriptionManager.new SaveTask().run();
		descriptionManager = null;
		activePlugin = null;
	}

}
