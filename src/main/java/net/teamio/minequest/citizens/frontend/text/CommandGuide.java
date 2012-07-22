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
package net.teamio.minequest.citizens.frontend.text;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import net.teamio.minequest.citizens.frontend.content.Content;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandGuide {

	private static Map<String,CommandGuide> playersInGuide = new LinkedHashMap<String,CommandGuide>();

	public static Set<String> getPlayersInGuide(){
		return Collections.unmodifiableSet(playersInGuide.keySet());
	}
	
	public static CommandGuide startPlayerGuide(Content content, Player player){
		CommandGuide c = new CommandGuide(content,player);
		playersInGuide.put(player.getName(),c);
		return c;
	}
	
	public static CommandGuide getPlayerGuide(String playerName){
		return playersInGuide.get(playerName);
	}

	public static void removePlayerInGuide(String playerName){
		playersInGuide.remove(playerName);
	}

	private Player player;
	private Content content;

	private CommandGuide(Content content, Player player){
		this.player = player;
		setContent(content);
	}

	public void setContent(Content content){
		this.content = content;
		displayTitle();
		displayMain();
		if (content.getFirstText()==null && content.getSecondText()==null && content.getThirdText()==null)
			onExitChoice();
		else {
			displayChoices();
			showAskingChoice();
		}
	}

	public void displayTitle(){
		player.sendMessage(ChatColor.GRAY + "-----------------------------------------");
		player.sendMessage(ChatColor.GRAY + content.getTitle());
		player.sendMessage(ChatColor.GRAY + "-----------------------------------------");
	}

	public void displayMain(){
		player.sendMessage(content.getMainText().split("\n"));
	}

	public void displayChoices(){
		if (content.getFirstText()!=null){
			String send = ChatColor.AQUA + "[1]" + ChatColor.RESET + " ";
			send+=content.getFirstText();
			player.sendMessage(send);
		}
		if (content.getSecondText()!=null){
			String send = ChatColor.AQUA + "[2]" + ChatColor.RESET + " ";
			send+=content.getSecondText();
			player.sendMessage(send);
		}
		if (content.getThirdText()!=null){
			String send = ChatColor.AQUA + "[3]" + ChatColor.RESET + " ";
			send+=content.getThirdText();
			player.sendMessage(send);
		}
		String again = ChatColor.BLUE + "[9]" + ChatColor.RESET + " Display Again";
		player.sendMessage(again);
		String exit = ChatColor.BLUE + "[0]" + ChatColor.RESET + " Exit";
		player.sendMessage(exit);
	}

	public void showAskingChoice(){
		player.sendMessage(ChatColor.GRAY + "-----------------------------------------");
		player.sendMessage(ChatColor.GRAY + "Enter your choice into the chatbox:");
	}
	
	public void showInvalidChoice(){
		player.sendMessage(ChatColor.GRAY + "-----------------------------------------");
		player.sendMessage(ChatColor.GRAY + "That's an invalid choice.");
		player.sendMessage(ChatColor.GRAY + "Try again or type \"9\" to display again.");
	}

	public void onFirstChoice(){
		if (content.getFirstText()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.onFirstText());
	}

	public void onSecondChoice(){
		if (content.getSecondText()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.onSecondText());
	}

	public void onThirdChoice(){
		if (content.getThirdText()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.onThirdText());
	}

	public void onDisplayChoice(){
		displayTitle();
		displayMain();
		displayChoices();
		showAskingChoice();
	}

	public void onExitChoice(){
		player.sendMessage(ChatColor.GRAY + "-----------------------------------------");
		player.sendMessage(ChatColor.GRAY + "Exited menu.");
		removePlayerInGuide(player.getName());
	}

}
