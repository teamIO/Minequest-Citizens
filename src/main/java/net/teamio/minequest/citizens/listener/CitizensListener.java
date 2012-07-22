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
package net.teamio.minequest.citizens.listener;

import net.citizensnpcs.api.event.CitizensReloadEvent;
import net.citizensnpcs.api.event.NPCDespawnEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.event.NPCSelectEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.teamio.minequest.citizens.MQAddonCitizens;
import net.teamio.minequest.citizens.frontend.Frontend;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CitizensListener implements Listener {

	@EventHandler
	public void onCitizensReload(CitizensReloadEvent e) {
		
	}

	@EventHandler
	public void onNPCDespawn(NPCDespawnEvent e) {

	}

	@EventHandler
	public void onNPCLeftClick(NPCLeftClickEvent e) {
		if (MQAddonCitizens.descriptionManager.activeNPCs.contains(e.getNPC().getId())){
			Frontend.openFrontend(e.getClicker(), e.getNPC());
		}
	}

	@EventHandler
	public void onNPCRightClick(NPCRightClickEvent e) {

	}

	@EventHandler
	public void onNPCSelect(NPCSelectEvent e) {

	}

	@EventHandler
	public void onNPCSpawn(NPCSpawnEvent e) {
		
	}

}
