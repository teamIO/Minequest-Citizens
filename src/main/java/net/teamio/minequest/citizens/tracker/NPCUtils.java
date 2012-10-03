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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestDetailsUtils;
import com.theminequest.MineQuest.API.Tracker.LogStatus;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;

public class NPCUtils {
	
	/**
	 * Get a list of quests this player can obtain.<br>
	 * This follows the content system, in which:<br>
	 * <ul>
	 * <li>If the List size is 0, then there are no available quests.</li>
	 * <li>If the List size is greater than 0, then there are quests.</li>
	 * <li>If the List <b>object</b> is <code>null</code>, then there is an active quest given.</li>
	 * </ul>
	 * @param player Player to lookup
	 * @param desc NPC to lookup
	 * @return List of quests, or <code>null</code>.
	 */
	public static List<String> getAvailableQuests(Player player, NPCDescription desc) {
		List<String> quests = desc.getQuests();
		List<String> availablequests = new ArrayList<String>();
		for (String q : quests){
			LogStatus l = QuestStatisticUtils.hasQuest(player.getName(), q);
			if (l==LogStatus.GIVEN || l==LogStatus.ACTIVE) {
				availablequests = null;
				break;
			} else if (l==LogStatus.UNKNOWN) {
				availablequests.add(q);
			} else {
				QuestDetails details = Managers.getQuestManager().getDetails(q);
				if (details!=null){
					if (QuestDetailsUtils.getRequirementsMet(details, player))
						availablequests.add(q);
				}
			}
		}
		return availablequests;
	}

}
