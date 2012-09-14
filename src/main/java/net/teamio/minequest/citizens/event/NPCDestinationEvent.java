package net.teamio.minequest.citizens.event;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import com.theminequest.MineQuest.API.CompleteStatus;
import com.theminequest.MineQuest.API.Managers;
import com.theminequest.MineQuest.API.Events.DelayedQuestEvent;
import com.theminequest.MineQuest.API.Events.UserQuestEvent;
import com.theminequest.MineQuest.API.Group.Group;
import com.theminequest.MineQuest.API.Quest.QuestDetails;

public class NPCDestinationEvent extends DelayedQuestEvent implements UserQuestEvent {
	
	private long delay;
	private int taskid;
	private int npcid;
	private String name;
	private Group group;
	
	@Override
	public String getDescription() {
		return "Go to " + name + "!";
	}
	
	@Override
	public long getDelay() {
		return delay;
	}
	
	@Override
	public boolean delayedConditions() {
		List<Player> py = group.getMembers();
		String worldname = getQuest().getDetails().getProperty(QuestDetails.QUEST_WORLD);
		for (Player p : py){
			if (!p.getWorld().getName().equals(worldname))
				continue;
			if (p.getLocation().distance(getLocation())<=2)
				return true;
		}
		return false;
	}
	
	@Override
	public void parseDetails(String[] details) {
		delay = Long.parseLong(details[0]);
		taskid = Integer.parseInt(details[1]);
		npcid = Integer.parseInt(details[2]);
		NPC npc = CitizensAPI.getNPCRegistry().getById(npcid);
		if (npc == null)
			throw new IllegalArgumentException("No such NPC!");
		name = npc.getName();
		group = Managers.getQuestGroupManager().get(getQuest());
	}
	
	@Override
	public CompleteStatus action() {
		return CompleteStatus.SUCCESS;
	}
	
	@Override
	public Integer switchTask() {
		return taskid;
	}
	
	private Location getLocation() {
		return CitizensAPI.getNPCRegistry().getById(npcid).getBukkitEntity().getLocation();
	}
}
