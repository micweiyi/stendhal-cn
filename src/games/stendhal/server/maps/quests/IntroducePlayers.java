package games.stendhal.server.maps.quests;

import games.stendhal.server.*;
import games.stendhal.server.maps.*;
import games.stendhal.server.entity.Player;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.Chest;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.scripting.ScriptAction;
import games.stendhal.server.scripting.ScriptCondition;

import marauroa.common.game.IRPZone;

/** 
 * QUEST: Introduce new players to game
 * PARTICIPANTS: 
 * - Tad
 * - Margaret
 * - Ilisa
 * 
 * STEPS: 
 * - Tad ask you to buy a flask to give it to Margaret.
 * - Margaret sell you a flask
 * - Tad thanks you and ask you to take the flask to Ilisa
 * - Ilisa ask you for a rat corpse and a few herbs.
 * - Return the created dress potion to Tad.
 *
 * REWARD: 
 * - 170 XP
 * - 10 gold coins 
 *
 * REPETITIONS:
 * - As much as wanted.
 */
public class IntroducePlayers implements IQuest {
	private StendhalRPWorld world;

	private NPCList npcs;

	private void step_1() {
		SpeakerNPC npc = npcs.get("Tad");

		npc.add(1,
				new String[] { "task", "quest" },
				null,
				1,
				null,
				new SpeakerNPC.ChatAction() {
					public void fire(Player player, String text, SpeakerNPC engine) {
						if (player.isQuestCompleted("introduce_players")) {
							engine.say("I have nothing for you now.");
						} else {
							engine.say("I need you to get a #flask from someone.");
						}
					}
				});

		/** In case Quest is completed */
		npc.add(1,
				"flask",
				new SpeakerNPC.ChatCondition() {
					public boolean fire(Player player, SpeakerNPC npc) {
						return player.isQuestCompleted("introduce_players");
					}
				},
				1,
				"You already did the quest.",
				null);

		/** If quest is not started yet, start it. */
		npc.add(1,
				"flask",
				new SpeakerNPC.ChatCondition() {
					public boolean fire(Player player, SpeakerNPC npc) {
						return !player.hasQuest("introduce_players");
					}
				},
				60,
				"Could you buy a flask from #Margaret?",
				null);

		npc.add(60,
				"yes",
				null,
				1,
				null,
				new SpeakerNPC.ChatAction() {
					public void fire(Player player, String text, SpeakerNPC engine) {
						engine.say("Nice! Please hurry up!");
						player.setQuest("introduce_players", "start");
					}
				});

		npc.add(60,
				"no",
				null,
				1,
				"Ok. But you should really consider it.|There is a nice reward :).",
				null);

		npc.add(60,
				"margaret",
				null,
				60,
				"Margaret is the tavern maid that work in Semos tavern. So will you do it?",
				null);

		/** Remind player about the quest */
		npc.add(1,
				"flask",
				new SpeakerNPC.ChatCondition() {
					public boolean fire(Player player, SpeakerNPC npc) {
						return player.hasQuest("introduce_players")
								&& player.getQuest("introduce_players").equals("start")
								&& !player.isEquipped("flask");
					}
				},
				1,
				"I really need that #flask now! Go to talk with #Margaret.",
				null);

		npc.add(1,
				"margaret",
				null,
				1,
				"Margaret is the tavern maid that work in Semos tavern.",
				null);
	}

	private void step_2() {
		/** Just buy the stuff from Margaret. It isn't a quest */
	}

	private void step_3() {
		SpeakerNPC npc = npcs.get("Tad");

		npc.add(0,
				new String[] { "hi", "hello", "hola" },
				new SpeakerNPC.ChatCondition() {
					public boolean fire(Player player, SpeakerNPC npc) {
						return player.hasQuest("introduce_players")
								&& player.getQuest("introduce_players").equals(
										"start") && player.isEquipped("flask");
					}
				},
				1,
				null,
				new SpeakerNPC.ChatAction() {
					public void fire(Player player, String text,
							SpeakerNPC engine) {
						engine.say("Ok!, I see you have the flask! Now I need you to grab it to #Ilisa");
						StackableItem money = (StackableItem) world
								.getRuleManager().getEntityManager().getItem(
										"money");
						money.setQuantity(10);
						player.equip(money);
						player.addXP(10);

						world.modify(player);

						player.setQuest("introduce_players", "ilisa");
					}
				});

		npc.add(1,
				"ilisa",
				null,
				1,
				"Ilisa is the summon healer at Semos temple.",
				null);
	}

	private void step_4() {
		SpeakerNPC npc = npcs.get("Ilisa");

		npc.add(0,
				new String[] { "hi", "hello", "hola" },
				new SpeakerNPC.ChatCondition() {
					public boolean fire(Player player, SpeakerNPC npc) {
						return player.hasQuest("introduce_players")
								&& player.getQuest("introduce_players").equals(
										"ilisa");
					}
				},
				1,
				null,
				new SpeakerNPC.ChatAction() {
					public void fire(Player player, String text,
							SpeakerNPC engine) {
						if (player.isEquipped("flask")) {
							player.drop("flask");
							engine.say("Thanks for the flask. Please I need a rat #corpse and a few #herbs to create the potion for #Tad.");
							player.addXP(10);

							world.modify(player);

							player.setQuest("introduce_players", "corpse&herbs");
						} else {
							engine.say("Weren't you supposed to have a flask for me? Go and get a flask.");
						}
					}
				});

		npc.add(1,
				"corpse",
				null,
				1,
				"There are tons of rats around Semos. They are a plague.",
				null);
		
		npc.add(1,
				"herbs",
				null,
				1,
				"North of Semos, near the tree grove, grows a herb called arandula.",
				null);
		
		npc.add(1,
				"tad",
				null,
				1,
				"He need a very powerful potion to heal himself. He offers a good reward to anyone who helps him.",
				null);
	}

	private void step_5() {
		SpeakerNPC npc = npcs.get("Ilisa");

		npc.add(0,
				new String[] { "hi", "hello", "hola" },
				new SpeakerNPC.ChatCondition() {
					public boolean fire(Player player, SpeakerNPC npc) {
						return player.hasQuest("introduce_players")
								&& player.getQuest("introduce_players").equals(
										"corpse&herbs");
					}
				},
				1,
				null,
				new SpeakerNPC.ChatAction() {
					public void fire(Player player, String text,
							SpeakerNPC engine) {
						Item item = player.drop("arandula");
						if (item != null) {
							engine.say("WOOOMMM!!! WUUOOAAAANNNN!!! WUUUUUNNN!!!| Tell #Tad that #potion is done and he should come here.");
							player.addXP(50);

							world.modify(player);

							player.setQuest("introduce_players", "potion");
						} else {
							engine.say("Don't have #herbs yet?");
						}
					}
				});

		npc.add(1,
				"potion",
				null,
				1,
				"The potion that #Tad is waiting for",
				null);
	}

	private void step_6() {
		SpeakerNPC npc = npcs.get("Tad");

		npc.add(0,
				new String[] { "hi", "hello", "hola" },
				new SpeakerNPC.ChatCondition() {
					public boolean fire(Player player, SpeakerNPC npc) {
						return player.hasQuest("introduce_players")
								&& player.getQuest("introduce_players").equals(
										"potion");
					}
				},
				1,
				null,
				new SpeakerNPC.ChatAction() {
					public void fire(Player player, String text,
							SpeakerNPC engine) {
						engine.say("Thanks! I will go talk with Ilisa as soon as possible.");
						player.addXP(100);
						world.modify(player);

						player.setQuest("introduce_players", "done");
					}
				});
	}

	private void initialize() {
		// place a chest full of arandula north of Semos
		StendhalRPZone zone = (StendhalRPZone) world.getRPZone(new IRPZone.ID(
				"0_semos_plains_n"));

		chest = new Chest();
		zone.assignRPObjectID(chest);
		chest.set(106, 47);
		chest.add(world.getRuleManager().getEntityManager().getItem(
						"arandula"));
		chest.add(world.getRuleManager().getEntityManager().getItem(
						"arandula"));
		chest.add(world.getRuleManager().getEntityManager().getItem(
						"arandula"));
		chest.add(world.getRuleManager().getEntityManager().getItem(
						"arandula"));
		zone.add(chest);

		/** Add a script to fill automatically the chest. */
		StendhalScriptSystem scripts = StendhalScriptSystem.get();
		scripts.addScript(new ScriptCondition() {
			public boolean fire() {
				return chest.size() < 1;
			}
		}, new ScriptAction() {
			public void fire() {
				chest.add(world.getRuleManager().getEntityManager().getItem(
						"arandula"));
			}
		});
	}

	public Chest chest;

	public IntroducePlayers(StendhalRPWorld w, StendhalRPRuleProcessor rules) {
		this.npcs = NPCList.get();
		this.world = w;

		initialize();

		step_1();
		step_2();
		step_3();
		step_4();
		step_5();
		step_6();
	}
}