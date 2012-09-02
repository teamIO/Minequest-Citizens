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
import net.teamio.minequest.citizens.statistic.NPCStatisticUtils;
import net.teamio.minequest.citizens.tracker.NPCDescription;
import net.teamio.minequest.citizens.tracker.NPCUtils;

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
		
		// if this player doesn't have this NPC assigned, screw them.
		if (!NPCStatisticUtils.hasNPC(player.getName(), npc.getId())){
			maintext = npc.getWhoareyoumessage();
			firsttext = null;
			secondtext = null;
			thirdtext = null;
			return;
		}
		
		// get list of quests available
		List<String> availablequests = NPCUtils.getAvailableQuests(getPlayer(), npc);
		if (availablequests == null) {
			maintext = npc.getActivemessage();
			firsttext = null;
			secondtext = null;
			thirdtext = null;
			return;
		} else if (availablequests.size()==0) {
			StringBuilder b = new StringBuilder();
			b.append(npc.getNoquestmessage()).append(QuestDetailsUtils.CODE_NEWLINE_SEQ);
			if (npc.getRecommend().size()!=0){
				b.append("Maybe you could ask for quests from other contacts...");
				for (Integer i : npc.getRecommend()){
					NPC nc = CitizensAPI.getNPCRegistry().getById(i);
					if (nc==null)
						continue;
					b.append(QuestDetailsUtils.CODE_NEWLINE_SEQ + "      - ").append(nc.getFullName());
					// by default, this assigns these players access to these NPCS
					// FIXME make configurable
					NPCStatisticUtils.assignNPCToPlayer(getPlayer().getName(), i);
				}
			}
			maintext = b.toString();
			firsttext = null;
			secondtext = null;
			thirdtext = null;
		} else {
			StringBuilder builder = new StringBuilder();
			builder.append(npc.getHavequestmessage() + QuestDetailsUtils.CODE_NEWLINE_SEQ + QuestDetailsUtils.CODE_NEWLINE_SEQ);
			first = Managers.getQuestManager().getDetails(availablequests.get(0));
			builder.append(QuestDetailsUtils.getOverviewString(first));
			firsttext = "Accept " + first.getProperty(QuestDetails.QUEST_DISPLAYNAME);
			if (availablequests.size()>1){
				builder.append(QuestDetailsUtils.CODE_NEWLINE_SEQ + ChatColor.YELLOW + "========== | OR | ==========" + QuestDetailsUtils.CODE_NEWLINE_SEQ);
				second = Managers.getQuestManager().getDetails(availablequests.get(1));
				builder.append(QuestDetailsUtils.getOverviewString(second));
				secondtext = "Accept " + second.getProperty(QuestDetails.QUEST_DISPLAYNAME);
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
