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
package net.teamio.minequest.citizens.event;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.teamio.minequest.citizens.statistic.NPCStatisticUtils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.QuestEvent;

public class NPCAssignEvent extends QuestEvent {

	private int id;
	private String name;
	private String location;
	
	/*
	 * (non-Javadoc)
	 * @see com.theminequest.MineQuest.API.Events.QuestEvent#parseDetails(java.lang.String[])
	 * [0] NPC ID
	 */
	@Override
	public void parseDetails(String[] details) {
		id = Integer.parseInt(details[0]);
		NPC n = CitizensAPI.getNPCRegistry().getById(id);
		if (n==null)
			throw new RuntimeException("ID doesn't exist!");
		name = n.getName();
		location = n.getBukkitEntity().getLocation().getWorld().getName();
	}

	@Override
	public boolean conditions() {
		return true;
	}

	@Override
	public CompleteStatus action() {
		for (Player p : Managers.getQuestGroupManager().get(getQuest()).getMembers()){
			NPCStatisticUtils.assignNPCToPlayer(p.getName(), id);
			p.sendMessage(ChatColor.YELLOW + "[NPC]" + ChatColor.WHITE + " You have a new contact!");
			p.sendMessage(ChatColor.YELLOW + "[NPC]" + ChatColor.AQUA + " " + name + ChatColor.WHITE + " is located in " + ChatColor.GOLD + location + ChatColor.WHITE + ".");
		}
		return CompleteStatus.SUCCESS;
	}

	@Override
	public Integer switchTask() {
		return null;
	}

}
