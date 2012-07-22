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
package net.teamio.minequest.citizens.frontend.content;

import java.util.ArrayList;
import java.util.List;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.teamio.minequest.citizens.tracker.NPCDescription;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Quest.QuestDetails;
import com.theminequest.MineQuest.API.Quest.QuestDetailsUtils;
import com.theminequest.MineQuest.API.Tracker.LogStatus;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils;
import com.theminequest.MineQuest.API.Tracker.QuestStatisticUtils.QSException;

public class NPCContent extends Content {
	
	private NPCDescription npc;
	
	private String title;
	private String maintext;
	private String firsttext;
	private String secondtext;
	private String thirdtext;
	
	private QuestDetails first;
	private QuestDetails second;

	public NPCContent(Player player, NPCDescription npc) {
		super(player);
		this.npc = npc;
		
		// setup properties
		NPC n = CitizensAPI.getNPCRegistry().getById(npc.getId());
		title = n.getName();
		// get list of quests available
		List<String> quests = npc.getQuests();
		List<String> availablequests = new ArrayList<String>();
		for (String q : quests){
			LogStatus l = QuestStatisticUtils.hasQuest(getPlayer().getName(), q);
			if (l==LogStatus.GIVEN || l==LogStatus.ACTIVE) {
				availablequests = null;
				break;
			} else if (l==LogStatus.UNKNOWN) {
				availablequests.add(q);
			} else {
				QuestDetails details = Managers.getQuestManager().getDetails(q);
				if (details!=null){
					if (QuestDetailsUtils.requirementsMet(details, getPlayer()))
						availablequests.add(q);
				}
			}
		}
		if (availablequests == null) {
			maintext = "You already have an active quest that this contact gives. Finish that first.";
			firsttext = null;
			secondtext = null;
			thirdtext = null;
			return;
		} else if (availablequests.size()==0) {
			maintext = npc.getNoquestmessage();
			firsttext = null;
			secondtext = null;
			thirdtext = null;
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append(npc.getHavequestmessage() + "\n\n");
			first = Managers.getQuestManager().getDetails(availablequests.get(0));
			builder.append("Quest A: ").append("\n");
			builder.append(QuestDetailsUtils.getOverviewString(first));
			firsttext = "Accept A";
			if (availablequests.size()>1){
				builder.append("\n" + ChatColor.YELLOW + "========== | OR | ==========\n");
				second = Managers.getQuestManager().getDetails(availablequests.get(1));
				builder.append("Quest B: ").append("\n");
				builder.append(QuestDetailsUtils.getOverviewString(second));
				secondtext = "Accept B";
			} else {
				second = null;
			}
			thirdtext = null;
			maintext = builder.toString();
		}
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getMainText() {
		return maintext;
	}

	@Override
	public String getFirstText() {
		return firsttext;
	}

	@Override
	public String getSecondText() {
		return secondtext;
	}

	@Override
	public String getThirdText() {
		return thirdtext;
	}

	@Override
	public Content onFirstText() {
		try {
			QuestStatisticUtils.giveQuest(getPlayer().getName(), (String) first.getProperty(QuestDetails.QUEST_NAME));
			maintext = first.getProperty(QuestDetails.QUEST_ACCEPT);
			firsttext = null;
			secondtext = null;
			thirdtext = null;
			title = first.getProperty(QuestDetails.QUEST_DISPLAYNAME);
		} catch (QSException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	@Override
	public Content onSecondText() {
		if (second == null)
			return this;
		try {
			QuestStatisticUtils.giveQuest(getPlayer().getName(), (String) second.getProperty(QuestDetails.QUEST_NAME));
			maintext = second.getProperty(QuestDetails.QUEST_ACCEPT);
			firsttext = null;
			secondtext = null;
			thirdtext = null;
			title = second.getProperty(QuestDetails.QUEST_DISPLAYNAME);
		} catch (QSException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	@Override
	public Content onThirdText() {
		return this;
	}

}
