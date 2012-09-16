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
package net.teamio.minequest.citizens.statistic;

import java.util.List;

import net.teamio.minequest.citizens.MQAddonCitizens;

import com.theminequest.MineQuest.API.Managers;

public class NPCStatisticUtils {
	
	public static void assignNPCToPlayer(String playerName, int id) {
		if (!hasNPC(playerName,id)) {
			NPCStatistic n = Managers.getStatisticManager().createStatistic(playerName, NPCStatistic.class);
			n.setNpcid(id);
			Managers.getStatisticManager().saveStatistic(n, NPCStatistic.class);
		}
	}
	
	public static void deassignNPCFromPlayer(String playerName, int id) {
		List<NPCStatistic> statistics = Managers.getStatisticManager().getAllStatistics(playerName, NPCStatistic.class);
		for (NPCStatistic n : statistics) {
			if (n.getNpcid()==id){
				Managers.getStatisticManager().removeStatistic(n, NPCStatistic.class);
				return;
			}
		}
	}
	
	public static boolean hasNPC(String playerName, int id) {
		if (!MQAddonCitizens.enableAssignments)
			return true;
		List<NPCStatistic> statistics = Managers.getStatisticManager().getAllStatistics(playerName, NPCStatistic.class);
		for (NPCStatistic n : statistics) {
			if (n.getNpcid()==id)
				return true;
		}
		return false;
	}

}
