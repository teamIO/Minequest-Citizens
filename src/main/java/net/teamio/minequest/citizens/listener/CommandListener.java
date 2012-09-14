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

import net.teamio.minequest.citizens.frontend.text.CommandGuide;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class CommandListener implements Listener {
	
	private Object lock;
	
	public CommandListener() {
		lock = new Object();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerChat(AsyncPlayerChatEvent e){
		synchronized(lock) {
			if (CommandGuide.getPlayersInGuide().contains(e.getPlayer().getName())){
				e.setCancelled(true);
				CommandGuide guide = CommandGuide.getPlayerGuide(e.getPlayer().getName());
				String msg = e.getMessage();
				if (!guide.isTextGuide()) {
					try {
						int choice = Integer.parseInt(msg);
						switch (choice) {
						case 1:
							guide.onFirstChoice();
							return;
						case 2:
							guide.onSecondChoice();
							return;
						case 3:
							guide.onThirdChoice();
							return;
						case 4:
							guide.onFourthChoice();
							return;
						case 5:
							guide.onFifthChoice();
							return;
						case 6:
							guide.onSixthChoice();
							return;
						case 7:
							guide.onSeventhChoice();
							return;
						case 8:
							guide.onEighthChoice();
							return;
						case 9:
							guide.onDisplayChoice();
							return;
						case 0:
							guide.onExitChoice();
							return;
						default:
							guide.showInvalidChoice();
							return;
						}
					} catch (NumberFormatException ex) {
						guide.showInvalidChoice();
					}
				} else {
					guide.onTextEntry(msg);
				}
			}
		}
	}
}
