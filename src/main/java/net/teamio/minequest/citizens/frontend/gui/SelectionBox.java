package net.teamio.minequest.citizens.frontend.gui;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.teamio.minequest.citizens.frontend.content.Content;

import org.getspout.spoutapi.gui.GenericComboBox;

public class SelectionBox extends GenericComboBox {
	
	private Map<Integer,Integer> contentmapping;
	private GUIGuide guide;
	
	public SelectionBox(GUIGuide guide) {
		contentmapping = new LinkedHashMap<Integer,Integer>();
		this.guide = guide;
	}
	
	public void setContent(Content content) {
		contentmapping.clear();
		List<String> items = new LinkedList<String>();
		items.add("- Select -");
		
		int counter = 1;
		if (content.get1()!=null) {
			contentmapping.put(counter, 1);
			items.add(content.get1());
			counter++;
		}
		if (content.get2()!=null) {
			contentmapping.put(counter, 2);
			items.add(content.get2());
			counter++;
		}
		if (content.get3()!=null) {
			contentmapping.put(counter, 3);
			items.add(content.get3());
			counter++;
		}
		if (content.get4()!=null) {
			contentmapping.put(counter, 4);
			items.add(content.get4());
			counter++;
		}
		if (content.get5()!=null) {
			contentmapping.put(counter, 5);
			items.add(content.get5());
			counter++;
		}
		if (content.get6()!=null) {
			contentmapping.put(counter, 6);
			items.add(content.get6());
			counter++;
		}
		if (content.get7()!=null) {
			contentmapping.put(counter, 7);
			items.add(content.get7());
			counter++;
		}
		if (content.get8()!=null) {
			contentmapping.put(counter, 8);
			items.add(content.get8());
			counter++;
		}
		
		this.setItems(items);
		
		if (items.size()==1)
			this.setEnabled(false);
		else {
			this.setEnabled(true);
		}
	}

	@Override
	public void onSelectionChanged(int i, String text) {
		if (contentmapping.get(i)==null)
			return;
		guide.onSelect(contentmapping.get(i));
	}
	
}
