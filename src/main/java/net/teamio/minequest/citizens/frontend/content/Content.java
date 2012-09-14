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

import org.bukkit.entity.Player;

public abstract class Content {
	
	private Player player;
	
	public Content(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public abstract boolean isTextEntry();
	
	public abstract String getTitle();
	public abstract String getMain();
	public String getExit() {
		return "Exit";
	}
	
	public abstract String get1();
	public abstract String get2();
	public abstract String get3();
	public abstract String get4();
	public abstract String get5();
	public abstract String get6();
	public abstract String get7();
	public abstract String get8();
	
	public abstract Content on1();
	public abstract Content on2();
	public abstract Content on3();
	public abstract Content on4();
	public abstract Content on5();
	public abstract Content on6();
	public abstract Content on7();
	public abstract Content on8();
	public abstract Content onTextEntry(String text);
}
