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
	
	public abstract String getTitle();
	public abstract String getMainText();
	public abstract String getFirstText();
	public abstract String getSecondText();
	public abstract String getThirdText();
	
	public abstract Content onFirstText();
	public abstract Content onSecondText();
	public abstract Content onThirdText();
}
