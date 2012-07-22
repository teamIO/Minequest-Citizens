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

import net.teamio.minequest.citizens.MQAddonCitizens;
import net.teamio.minequest.citizens.frontend.text.CommandGuide;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

public class ExitListener implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		CommandGuide.removePlayerInGuide(e.getPlayer().getName());
		removeSpout(e);
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent e){
		CommandGuide.removePlayerInGuide(e.getPlayer().getName());
		removeSpout(e);
	}
	
	public void removeSpout(PlayerEvent event){
		try {
			Class.forName("org.getspout.spoutapi.Spout");
			SpoutPlayer sPlayer = (SpoutPlayer) event.getPlayer();
			sPlayer.getMainScreen().removeWidgets(MQAddonCitizens.activePlugin);
		} catch (ClassNotFoundException e) {}
	}

}
