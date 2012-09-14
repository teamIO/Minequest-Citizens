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
package net.teamio.minequest.citizens.command;

import java.util.ArrayList;
import java.util.List;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.teamio.minequest.citizens.MQAddonCitizens;
import net.teamio.minequest.citizens.frontend.Frontend;
import net.teamio.minequest.citizens.statistic.NPCStatistic;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.I18NMessage;
import com.theminequest.MineQuest.MineQuest;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Utils.ChatUtils;
import com.theminequest.MineQuest.Frontend.Command.CommandFrontend;

public class ContactsCommandFrontend extends CommandFrontend {

	public ContactsCommandFrontend() {
		super("contacts");
		MineQuest.commandListener.helpmenu.put("contacts", "(NPC) View Contacts.");
	}

	@Override
	public void help(CommandSender p, String[] args) {
		noOptionSpecified(p,args);
	}
	
	public void call(Player p, String[] args) {
		List<NPCStatistic> npcs = Managers.getStatisticManager().getAllStatistics(p.getName(), NPCStatistic.class);
		for (NPCStatistic n : npcs) {
			NPC npc = CitizensAPI.getNPCRegistry().getById(n.getNpcid());
			if (npc==null)
				continue;
			if (MQAddonCitizens.descriptionManager.activeNPCs.contains(npc.getId())){
				Frontend.openFrontend(p, npc);
				return;
			}
		}
		p.sendMessage("No such contact!");
	}

	@Override
	public boolean allowConsole() {
		return false;
	}
	
	private String formatNPC(String name, String location) {
		String toreturn = "";
		toreturn += ChatColor.GREEN + name;
		for (int i=0; i<20-name.length(); i++)
			toreturn+=" ";
		for (int i=0; i<10; i++)
			toreturn+=" ";
		for (int i=0; i<30-location.length(); i++)
			toreturn+=" ";
		toreturn += ChatColor.YELLOW + location;
		return toreturn;
	}

	@Override
	public void noOptionSpecified(CommandSender p, String[] args) {
		List<String> tosend = new ArrayList<String>();
		tosend.add(ChatUtils.formatHeader("Active Contacts"));
		List<NPCStatistic> npcs = Managers.getStatisticManager().getAllStatistics(p.getName(), NPCStatistic.class);
		for (NPCStatistic n : npcs) {
			NPC npc = CitizensAPI.getNPCRegistry().getById(n.getNpcid());
			if (npc==null)
				continue;
			tosend.add(formatNPC(npc.getName(),npc.getBukkitEntity().getLocation().getWorld().getName()));
		}
		tosend.add(ChatUtils.formatHeader("To call a contact, use /contacts call <name>"));
		for (String s : tosend) {
			p.sendMessage(s);
		}
	}

}
