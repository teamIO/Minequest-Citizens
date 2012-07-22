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

import org.bukkit.configuration.file.YamlConfiguration;
import org.ini4j.InvalidFileFormatException;

public class NPCDescription {
	
	private File file;
	private int id;
	private String noquestmessage;
	private String havequestmessage;
	private List<String> quests;
	private List<Integer> recommend;
	
	public NPCDescription(int id) throws IOException{
		this.id = id;
		noquestmessage = "I don't seem to have anything for you to do.";
		havequestmessage = "Let's get started.";
		quests = new ArrayList<String>();
		recommend = new ArrayList<Integer>();
		file = new File(DescriptionManager.DESCRIPTION_LOCATION + File.separator + id + ".yaml");
	}

	/**
	 * Create an NPC description object from a file.
	 * @param f
	 * @throws InvalidFileFormatException
	 * @throws IOException
	 */
	public NPCDescription(File f) throws IOException {
		YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
		file = f;
		
		/*
		 * General NPC Properties. This includes the name, ID number,
		 * location of the NPC, and messages it tells the user.
		 * In addition, if you have Spoutcraft, you can give the NPC
		 * a skin and cape.
		 */
		id = config.getInt("id");
		noquestmessage = config.getString("noquest");
		havequestmessage = config.getString("havequest");
		
		/*
		 * Quests - What quests does this NPC make available?
		 */
		quests = config.getStringList("quests");
		
		/*
		 * Recommendations - When all quests are completed (all
		 * are non-repeating and this NPC has no more to give),
		 * which other NPCs do you want to recommend the player
		 * go to for quests? (by ID number)
		 */
		recommend = config.getIntegerList("recommend");
	}
	
	/**
	 * Save the NPC description to a file.
	 * @throws IOException if unable to save.
	 */
	public void save() throws IOException{
		YamlConfiguration config = new YamlConfiguration();
		config.set("id", id);
		config.set("noquest",noquestmessage);
		config.set("havequest",havequestmessage);
		config.set("quests", quests);
		config.set("recommend",recommend);
		config.save(file);
	}
	
	/* Automatically generated getters and setters by Eclipse */

	public int getId() {
		return id;
	}

	public String getNoquestmessage() {
		return noquestmessage;
	}

	public void setNoquestmessage(String noquestmessage) {
		this.noquestmessage = noquestmessage;
	}

	public String getHavequestmessage() {
		return havequestmessage;
	}

	public void setHavequestmessage(String havequestmessage) {
		this.havequestmessage = havequestmessage;
	}

	public List<String> getQuests() {
		return quests;
	}

	public void setQuests(List<String> quests) {
		this.quests = quests;
	}

	public List<Integer> getRecommend() {
		return recommend;
	}

	public void setRecommend(List<Integer> recommend) {
		this.recommend = recommend;
	}
	
}