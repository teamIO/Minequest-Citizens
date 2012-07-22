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

import com.alta189.simplesave.Field;
import com.alta189.simplesave.Id;
import com.alta189.simplesave.Table;
import com.theminequest.MineQuest.API.Tracker.Statistic;

@Table("mq_npc")
public class NPCStatistic extends Statistic {
	
	@Id
	private long id;
	
	@Field
	private int npcid;
	
	@Field
	private int status;
	
	@Field
	private String assignment;

	public int getNpcid() {
		return npcid;
	}

	public void setNpcid(int npcid) {
		this.npcid = npcid;
	}

	public NPCStatus getStatus() {
		if (status==1)
			return NPCStatus.ACTIVE;
		else if (status==2)
			return NPCStatus.ASSIGNED;
		else if (status==3)
			return NPCStatus.COMPLETED;
		else
			return NPCStatus.NEW;
	}

	public void setStatus(NPCStatus status) {
		switch(status){
		case ACTIVE:
			this.status = 1;
			return;
		case ASSIGNED:
			this.status = 2;
			return;
		case COMPLETED:
			this.status = 3;
			return;
		default:
			this.status = 0;
			return;
		}
	}

	public String getAssignment() {
		return assignment;
	}

	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	@Override
	public void setup() {}
	
	public static enum NPCStatus {
		
		/**
		 * Indicates that the player was just assigned to the NPC
		 * and therefore first-time greetings should be said.
		 */
		NEW,
		/**
		 * Indicates that the player currently gets quests from this NPC
		 */
		ACTIVE,
		/**
		 * Indicates that the player is currently on a quest from the NPC
		 */
		ASSIGNED,
		/**
		 * Indicates that the player may no longer get quests from this
		 * NPC (due the quest exhaustion or level outgrowth)
		 */
		COMPLETED;
	}

}
