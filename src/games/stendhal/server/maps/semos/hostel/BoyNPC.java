/***************************************************************************
 *                   (C) Copyright 2003-2016 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.semos.hostel;

import java.util.Map;

import games.stendhal.common.Direction;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.player.Player;

public class BoyNPC implements ZoneConfigurator {
	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildSemosTownhallArea(zone);
	}

	private void buildSemosTownhallArea(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Tad") {

			@Override
			protected void createPath() {
				setPath(null);
			}

			@Override
			protected void createDialog() {
				add(ConversationStates.IDLE,
						ConversationPhrases.GREETING_MESSAGES,
						new AndCondition(
								new GreetingMatchesNameCondition(getName()),
								new QuestNotStartedCondition("introduce_players"),
								new ChatCondition() {
									@Override
									public boolean fire(final Player player, final Sentence sentence, final Entity entity) {
										return !player.isGhost();
									}
								}),
				        ConversationStates.ATTENDING,
				        null,
				        new SayTextAction("嘘! 来这边, [name]! 我有个任务 #task 交给你."));

				// this is the condition for any other case while the quest is active, not covered by the quest.
				add(ConversationStates.IDLE,
						ConversationPhrases.GREETING_MESSAGES,
						new GreetingMatchesNameCondition(getName()), true,
				        ConversationStates.ATTENDING,
				        "*sniff* *sniff* 我病了，快帮我把药 #favour 拿来.",
				        null);

				add(ConversationStates.IDLE,
						ConversationPhrases.GREETING_MESSAGES,
						new AndCondition(new GreetingMatchesNameCondition(getName()),
								new QuestCompletedCondition("introduce_players")),
				        ConversationStates.ATTENDING,
				        null,
				        new SayTextAction("又见面了, [name]! 再次感谢，现在我觉得好多了."));

				addGoodbye();
			}

			/*
			 * (non-Javadoc)
			 * @see games.stendhal.server.entity.npc.SpeakerNPC#onGoodbye(games.stendhal.server.entity.RPEntity)
			 */
			@Override
			protected void onGoodbye(RPEntity player) {
				setDirection(Direction.LEFT);
			}

		};

		npc.addInitChatMessage(null, new ChatAction() {
			@Override
			public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
				if (!player.hasQuest("TadFirstChat")) {
					player.setQuest("TadFirstChat", "done");
					((SpeakerNPC) raiser.getEntity()).listenTo(player, "hi");
				}
			}
		});

		npc.setEntityClass("childnpc");
		npc.setDescription("这个年轻的男孩叫 Tad. 他的脸色苍白，看起来病得很重.");

		npc.setPosition(18, 21);
		npc.setDirection(Direction.LEFT);

		npc.initHP(100);
		zone.add(npc);
	}
}
