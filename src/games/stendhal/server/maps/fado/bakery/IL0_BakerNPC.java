package games.stendhal.server.maps.fado.bakery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.server.StendhalRPZone;
import games.stendhal.server.entity.npc.NPCList;
import games.stendhal.server.entity.npc.ProducerBehaviour;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.maps.ZoneConfigurator;
import games.stendhal.server.pathfinder.Path;

/**
 * Builds the bakery baker NPC.
 *
 * @author timothyb89
 */
public class IL0_BakerNPC implements ZoneConfigurator {
	private NPCList npcs = NPCList.get();


	//
	// ZoneConfigurator
	//

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(StendhalRPZone zone,
	 Map<String, String> attributes) {
		buildNPC(zone, attributes);
	}


	//
	// IL0_BakerNPC
	//

	private void buildNPC(StendhalRPZone zone,
	 Map<String, String> attributes) {
		SpeakerNPC baker = new SpeakerNPC("Linzo") {
			@Override
			protected void createPath() {
				List<Path.Node> nodes = new LinkedList<Path.Node>();
				// to the well
				nodes.add(new Path.Node(15, 2));
				// to a barrel
				nodes.add(new Path.Node(15, 7));
				// to the baguette on the table
				nodes.add(new Path.Node(13, 7));
				// around the table
				nodes.add(new Path.Node(13, 9));
				nodes.add(new Path.Node(10, 9));
				// to the sink
				nodes.add(new Path.Node(10, 11));
				// to the pizza/cake/whatever
				nodes.add(new Path.Node(7, 11));
				nodes.add(new Path.Node(7, 9));
				// to the pot
				nodes.add(new Path.Node(3, 9));
				// towards the oven
				nodes.add(new Path.Node(3, 3));
				nodes.add(new Path.Node(5, 3));
				// to the oven
				nodes.add(new Path.Node(5, 2));
				// one step back
				nodes.add(new Path.Node(5, 3));
				// towards the well
				nodes.add(new Path.Node(15, 3));

				setPath(nodes, true);
			}

			@Override
			protected void createDialog() {
				// addGreeting("Hi, most of the people are out of town at the moment.");
				addJob("I'm the local baker. Although we get most of our supplies from Semos City, there is still a lot of work we have to do.");
				addReply(Arrays.asList("flour", "meat", "carrot", "mushroom", "button_mushroom"), "Ados is short on supplies. We get most of our food from Semos City which is North of here.");
				addHelp("Ask me to make you a pie.");
				addGoodbye();

				// Linzo makes pies if you bring him flour, meat, carrot and a mushroom
				Map<String, Integer> requiredResources = new HashMap<String, Integer>();
				requiredResources.put("flour", new Integer(2));
				requiredResources.put("meat", new Integer(2));
				requiredResources.put("carrot", new Integer(1));
				requiredResources.put("button_mushroom", new Integer(1));

				ProducerBehaviour behaviour = new ProducerBehaviour(
						"arlindo_make_pie", "make", "pie", requiredResources, 7 * 60);

				addProducer(behaviour,
						"Hi! I bet you've heard about my famous pie and want me to #make one for you, am I right?");
			}
		};

		npcs.add(baker);
		zone.assignRPObjectID(baker);
		baker.put("class", "bakernpc");
		baker.setDirection(Direction.DOWN);
		baker.set(15, 2);
		baker.initHP(1000);
		zone.add(baker);
	}
}
