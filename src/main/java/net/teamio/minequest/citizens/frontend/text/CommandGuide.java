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

import com.theminequest.MineQuest.API.Quest.QuestDetailsUtils;

public class CommandGuide {

	private static Map<String,CommandGuide> playersInGuide = Collections.synchronizedMap(new LinkedHashMap<String,CommandGuide>());

	public static Set<String> getPlayersInGuide(){
		return Collections.unmodifiableSet(playersInGuide.keySet());
	}
	
	public static CommandGuide startPlayerGuide(Content content, Player player){
		CommandGuide c = new CommandGuide(content,player);
		if (!c.exited)
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
	private boolean exited;

	private CommandGuide(Content content, Player player){
		this.player = player;
		this.exited = false;
		setContent(content);
	}

	public void setContent(Content content){
		if (content==null)
			return;
		this.content = content;
		displayTitle();
		displayMain();
		if (!content.isTextEntry() && content.get1()==null && content.get2()==null && content.get3()==null
				&& content.get5()==null && content.get6()==null
				&& content.get7()==null && content.get8()==null) {
			onExitChoice();
		} else if (content.isTextEntry()) {
			showTextChoice();
		} else {
			displayChoices();
			showAskingChoice();
		}
	}
	
	public boolean isTextGuide(){
		return content.isTextEntry();
	}

	public void displayTitle(){
		player.sendMessage(ChatColor.GRAY + "-----------------------------------------");
		player.sendMessage(ChatColor.GRAY + content.getTitle());
		player.sendMessage(ChatColor.GRAY + "-----------------------------------------");
	}

	public void displayMain(){
		player.sendMessage(content.getMain().split(QuestDetailsUtils.CODE_NEWLINE_SEQ));
	}

	public void displayChoices(){
		if (content.get1()!=null){
			String send = ChatColor.AQUA + "[1]" + ChatColor.RESET + " ";
			send+=content.get1();
			player.sendMessage(send);
		}
		if (content.get2()!=null){
			String send = ChatColor.AQUA + "[2]" + ChatColor.RESET + " ";
			send+=content.get2();
			player.sendMessage(send);
		}
		if (content.get3()!=null){
			String send = ChatColor.AQUA + "[3]" + ChatColor.RESET + " ";
			send+=content.get3();
			player.sendMessage(send);
		}
		if (content.get4()!=null){
			String send = ChatColor.AQUA + "[4]" + ChatColor.RESET + " ";
			send+=content.get4();
			player.sendMessage(send);
		}
		if (content.get5()!=null){
			String send = ChatColor.AQUA + "[5]" + ChatColor.RESET + " ";
			send+=content.get5();
			player.sendMessage(send);
		}
		if (content.get6()!=null){
			String send = ChatColor.AQUA + "[6]" + ChatColor.RESET + " ";
			send+=content.get6();
			player.sendMessage(send);
		}
		if (content.get7()!=null){
			String send = ChatColor.AQUA + "[7]" + ChatColor.RESET + " ";
			send+=content.get7();
			player.sendMessage(send);
		}
		if (content.get8()!=null){
			String send = ChatColor.AQUA + "[8]" + ChatColor.RESET + " ";
			send+=content.get8();
			player.sendMessage(send);
		}
		String again = ChatColor.BLUE + "[9]" + ChatColor.RESET + " Display Again";
		player.sendMessage(again);
		String exit = ChatColor.BLUE + "[0]" + ChatColor.RESET + " " + content.getExit();
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

	private void showTextChoice() {
		player.sendMessage(ChatColor.GRAY + "-----------------------------------------");
		player.sendMessage(ChatColor.GRAY + "Enter your response in the chatbox:");
	}

	public void onFirstChoice(){
		if (content.get1()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.on1());
	}

	public void onSecondChoice(){
		if (content.get2()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.on2());
	}

	public void onThirdChoice(){
		if (content.get3()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.on3());
	}
	
	public void onFourthChoice(){
		if (content.get4()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.on4());
	}

	public void onFifthChoice(){
		if (content.get5()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.on5());
	}
	
	public void onSixthChoice(){
		if (content.get6()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.on6());
	}
	
	public void onSeventhChoice(){
		if (content.get7()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.on7());
	}
	
	public void onEighthChoice(){
		if (content.get8()==null){
			showInvalidChoice();
			return;
		}
		setContent(content.on8());
	}
	
	public void onTextEntry(String text){
		if (!content.isTextEntry()){
			showInvalidChoice();
			return;
		}
		setContent(content.onTextEntry(text));
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
		this.exited = true;
	}

}
