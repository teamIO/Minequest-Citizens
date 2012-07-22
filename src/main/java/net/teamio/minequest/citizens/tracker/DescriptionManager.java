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
package net.teamio.minequest.citizens.tracker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import net.teamio.minequest.citizens.MQAddonCitizens;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.theminequest.MineQuest.API.Managers;

public class DescriptionManager {
	
	public static final String DESCRIPTION_LOCATION = Managers.getActivePlugin().getDataFolder().getAbsolutePath() + File.separator + "npc";
	public final List<Integer> activeNPCs;
	
	public DescriptionManager(){
		File dir = new File(DESCRIPTION_LOCATION);
		if (dir.exists()){
			if (!dir.isDirectory()){
				dir.delete();
				dir.mkdirs();
			}
		} else
			dir.mkdirs();
		YamlConfiguration active = YamlConfiguration.loadConfiguration(new File(DESCRIPTION_LOCATION + File.separator + "active.yaml"));
		List<Integer> a = active.getIntegerList("active");
		if (a==null)
			a = new ArrayList<Integer>();
		activeNPCs = a;
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(MQAddonCitizens.activePlugin, new SaveTask(), 1000, 1000);
	}
	
	public NPCDescription getNPCDescription(int id) throws IOException{
		File f = new File(DescriptionManager.DESCRIPTION_LOCATION + File.separator + id+".yaml");
		if (f.exists()) {
			try {
				return new NPCDescription(f);
			} catch (Exception e) {
				Managers.log(Level.SEVERE, "[NPC] Could not load description file for id " + id + "! Killing configuration!");
				e.printStackTrace();
			}
		}
		NPCDescription desc = new NPCDescription(id);
		desc.save();
		return desc;
	}
	
	public class SaveTask implements Runnable {
		
		@Override
		public void run() {
			File f = new File(DESCRIPTION_LOCATION + File.separator + "active.yaml");
			YamlConfiguration active = new YamlConfiguration();
			active.set("active",activeNPCs);
			try {
				active.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
