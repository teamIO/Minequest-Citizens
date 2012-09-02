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
package net.teamio.minequest.citizens.frontend.gui;

import java.util.logging.Logger;

import net.teamio.minequest.citizens.MQAddonCitizens;
import net.teamio.minequest.citizens.frontend.content.Content;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Screen;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.theminequest.MineQuest.API.Quest.QuestDetailsUtils;

public class GUIGuide extends GenericPopup {

	final GenericTextField guideField, guideInvisible;
	final GenericLabel guideName;
	final GenericButton close, firstbutton, secondbutton, thirdbutton;	
	public final SpoutPlayer player;
	public final Logger log = Logger.getLogger("Minecraft");

	public GUIGuide(Content content, SpoutPlayer player) {
		super();		
		this.player = player;

		GenericLabel label = new GenericLabel();
		label.setText("Available Quests");
		label.setAnchor(WidgetAnchor.CENTER_CENTER);
		label.shiftXPos(-35).shiftYPos(-122);		
		label.setPriority(RenderPriority.Lowest);
		label.setWidth(-1).setHeight(-1);

		guideName = new GenericLabel("TheGuideNameHere");
		guideName.setWidth(-1).setHeight(-1);
		guideName.setAnchor(WidgetAnchor.CENTER_CENTER);
		guideName.shiftXPos(-200).shiftYPos(-105);

		guideInvisible = new GenericTextField();
		guideInvisible.setWidth(150).setHeight(18);
		guideInvisible.setAnchor(WidgetAnchor.CENTER_CENTER);
		guideInvisible.shiftXPos(-200).shiftYPos(-110);			
		guideInvisible.setMaximumCharacters(30);
		guideInvisible.setMaximumLines(1);		
		guideInvisible.setVisible(false);

		GenericTexture border = new GenericTexture("http://www.almuramc.com/images/sguide.png");		
		border.setAnchor(WidgetAnchor.CENTER_CENTER);
		border.setPriority(RenderPriority.High);
		border.setWidth(626).setHeight(240);		
		border.shiftXPos(-220).shiftYPos(-128);

		guideField = new GenericTextField();
		guideField.setText("first guide goes here"); // The default text
		guideField.setEnabled(false);
		guideField.setAnchor(WidgetAnchor.CENTER_CENTER);
		guideField.setBorderColor(new Color(1.0F, 1.0F, 1.0F, 1.0F)); // White border
		guideField.setMaximumCharacters(1000);
		guideField.setMaximumLines(13);
		guideField.setHeight(160).setWidth(377);
		guideField.shiftXPos(-195).shiftYPos(-83);
		guideField.setMargin(0);

		close = new CloseButton(this);
		close.setAuto(true);
		close.setAnchor(WidgetAnchor.CENTER_CENTER);
		close.setHeight(18).setWidth(40);
		close.shiftXPos(142).shiftYPos(87);

		this.setTransparent(true);		
		attachWidgets(MQAddonCitizens.activePlugin, border, label);

		this.setTransparent(true);
		attachWidget(MQAddonCitizens.activePlugin, label);
		attachWidget(MQAddonCitizens.activePlugin, border);
		attachWidget(MQAddonCitizens.activePlugin, guideField);		
		attachWidget(MQAddonCitizens.activePlugin, close);
		attachWidget(MQAddonCitizens.activePlugin, guideName);
		attachWidget(MQAddonCitizens.activePlugin, guideInvisible);
		
		firstbutton = new FirstButton(this);
		firstbutton.setAuto(true);
		firstbutton.setAnchor(WidgetAnchor.CENTER_CENTER);
		firstbutton.setAuto(true).setHeight(18).setWidth(80).shiftXPos(-190).shiftYPos(87);
		attachWidget(MQAddonCitizens.activePlugin, firstbutton);

		secondbutton = new SecondButton(this);
		secondbutton.setAnchor(WidgetAnchor.CENTER_CENTER);
		secondbutton.setAuto(true).setHeight(18).setWidth(80).shiftXPos(-100).shiftYPos(87);
		attachWidget(MQAddonCitizens.activePlugin, secondbutton);

		thirdbutton = new ThirdButton(this);
		thirdbutton.setAnchor(WidgetAnchor.CENTER_CENTER);
		thirdbutton.setAuto(true).setHeight(18).setWidth(80).shiftXPos(-10).shiftYPos(87);
		attachWidget(MQAddonCitizens.activePlugin, thirdbutton);

		setContent(content);
	}

	private Content content;

	public void setContent(Content content) {
		if (content == null)
			return;

		this.content = content;

		guideName.setText(content.getTitle()).setWidth(-1);
		String[] split = content.getMainText().split(QuestDetailsUtils.CODE_NEWLINE_SEQ);
		String actual = "";
		for (String s : split)
			actual += s + "\n";
		guideField.setText(actual);
		if (content.getFirstText()!=null){
			firstbutton.setText(content.getFirstText());
			firstbutton.setVisible(true);
		} else
			firstbutton.setVisible(false);

		if (content.getSecondText()!=null){
			secondbutton.setText(content.getSecondText());
			secondbutton.setVisible(true);
		} else
			secondbutton.setVisible(false);

		if (content.getSecondText()!=null){
			secondbutton.setText(content.getSecondText());
			secondbutton.setVisible(true);
		} else
			secondbutton.setVisible(false);

		if (content.getThirdText()!=null){
			thirdbutton.setText(content.getThirdText());
			thirdbutton.setVisible(true);
		} else
			thirdbutton.setVisible(false);
	}

	public void onFirstClick() {
		setContent(content.onFirstText());
	}

	void onSecondClick() {
		setContent(content.onSecondText());
	}

	void onThirdClick() {
		setContent(content.onThirdText());
	}

	void onCloseClick() {
		Screen screen = ((SpoutPlayer) player).getMainScreen();				
		screen.removeWidget(this);
		//player.getMainScreen().closePopup();
		player.closeActiveWindow();
	}

}
