package games.stendhal.server.maps.semos;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.server.StendhalRPZone;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.maps.ZoneConfigurator;
import games.stendhal.server.pathfinder.Path;

public class IL0_Bank implements ZoneConfigurator {
	private NPCList npcs = NPCList.get();


	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(StendhalRPZone zone,
	 Map<String, String> attributes) {
		buildSemosBankArea(zone, attributes);
	}


	private void buildSemosBankArea(StendhalRPZone zone,
	 Map<String, String> attributes) {
		SpeakerNPC npc = new SpeakerNPC("Dagobert") {
			@Override
			protected void createPath() {
				// NPC doesn't move
				List<Path.Node> nodes = new LinkedList<Path.Node>();
				setPath(nodes, false);
			}

			@Override
			protected void createDialog() {
				addGreeting("Welcome to the bank of Semos! Do you need #help on your personal chest?");
				addHelp("Follow the corridor to the right, and you will find the magic chests. You can store your belongings in any of them, and nobody else will be able to touch them!");
				addJob("I'm the Customer Advisor here at Semos Bank.");
				addGoodbye("It was a pleasure to serve you.");
			}
		};
		npcs.add(npc);
		zone.assignRPObjectID(npc);
		npc.put("class", "youngnpc");
		npc.set(9, 22);
		npc.setDirection(Direction.DOWN);
		npc.initHP(100);
		zone.add(npc);
	}
}
