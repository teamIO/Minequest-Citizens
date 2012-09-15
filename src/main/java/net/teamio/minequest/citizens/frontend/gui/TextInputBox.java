package net.teamio.minequest.citizens.frontend.gui;

import java.util.LinkedHashMap;

import org.getspout.spoutapi.gui.GenericTextField;

public class TextInputBox extends GenericTextField {
	
	private GUIGuide guide;
	
	public TextInputBox(GUIGuide guide) {
		this.guide = guide;
		this.setPlaceholder("Type your response here, then hit ENTER.");
	}

	@Override
	public void onTypingFinished() {
		guide.onTypingFinish(this.getText());
	}
	
}
