/***************************************************************************
 *                   (C) Copyright 2003-2015 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.DropItemAction;
import games.stendhal.server.entity.npc.action.EquipItemAction;
import games.stendhal.server.entity.npc.action.ExamineChatAction;
import games.stendhal.server.entity.npc.action.IncreaseKarmaAction;
import games.stendhal.server.entity.npc.action.IncreaseXPAction;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.SetQuestAction;
import games.stendhal.server.entity.npc.condition.AndCondition;
import games.stendhal.server.entity.npc.condition.GreetingMatchesNameCondition;
import games.stendhal.server.entity.npc.condition.NotCondition;
import games.stendhal.server.entity.npc.condition.PlayerHasItemWithHimCondition;
import games.stendhal.server.entity.npc.condition.QuestCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestInStateCondition;
import games.stendhal.server.entity.npc.condition.QuestNotCompletedCondition;
import games.stendhal.server.entity.npc.condition.QuestNotStartedCondition;
import games.stendhal.server.entity.npc.condition.QuestStartedCondition;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.Region;

/**
 * QUEST: Introduce new players to game <p>PARTICIPANTS:<ul>
 * <li> Tad
 * <li> Margaret
 * <li> Ilisa
 * <li> Ketteh Wehoh
 * </ul>
 *
 * <p>
 * STEPS:<ul>
 * <li> Tad asks you to buy a flask to give it to Margaret.
 * <li> Margaret sells you a flask
 * <li> Tad thanks you and asks you to take the flask to Ilisa
 * <li> Ilisa asks you for a few herbs.
 * <li> Return the created dress potion to Tad.
 * <li> Ketteh Wehoh will reminder player about Tad, if quest is started but not complete.
 * </ul>
 * <p>
 * REWARD:<ul>
 * <li> 270 XP
 * <li> some karma (4)
 * <li> 10 gold coins
 * </ul>
 * <p>
 * REPETITIONS:<ul>
 * <li> None.
 * </ul>
 */
public class MedicineForTad extends AbstractQuest {

	static final String ILISA_TALK_ASK_FOR_FLASK = "#Tad 要的药？他不是让你把 flask 带给他吗?";
	static final String ILISA_TALK_ASK_FOR_HERB = "啊, 我看见你拿着 flask. #Tad 需要药，对吧？ 唔... 我还需要点 #herb. 你能帮我取点吗?";
	static final String ILISA_TALK_DESCRIBE_HERB = "Semos 镇北面，挨着小树林，生长着一种名叫arandula的草药，这张图画的是这种草药的图片，所以快照图去找吧.";
	static final String ILISA_TALK_INTRODUCE_TAD = "他需要一种强心济来治好他的病，他会给帮助过他的人很不错的酬金。";
	static final String ILISA_TALK_REMIND_HERB = "你拿到这些#herbs 来制作 #medicine 了吗?";
	static final String ILISA_TALK_PREPARE_MEDICINE = "很好!谢谢你，现在我要把这些混合...加入这个...然后再滴点... 好了！你能让 #Tad 停下并收集这个吗？ 我想知道他在做些什么。";
	static final String ILISA_TALK_EXPLAIN_MEDICINE = "这是 #Tad 急需的东西.";

	static final String KETTEH_TALK_BYE_INTRODUCES_TAD = "再会，你见到Ted了，在旅店里？如果你有机会，请看看他，我听说他近来身体不太好。你可以在 Semos 村子里的旅店找到他，离Nishiya很近.";
	static final String KETTEH_TALK_BYE_REMINDS_OF_TAD = "再见，不要忘了去看看 Tad。我希望他好起来.";

	static final String TAD_TALK_GOT_FLASK = "Ok, 你得到了 flask!";
	static final String TAD_TALK_REWARD_MONEY = "拿着，这些钱足够你的开销了.";
	static final String TAD_TALK_FLASK_ILISA = "现在，我需要你带着它去找 #Ilisa... 她一看就明白接下来要做什么。";
	static final String TAD_TALK_REMIND_FLASK_ILISA = "我需要你带着flask去找 #Ilisa... 她一看就明白接下来要做什么。";
	static final String TAD_TALK_INTRODUCE_ILISA = "Ilisa 是 Semos 教堂的召唤治疗师。";
	static final String TAD_TALK_REMIND_MEDICINE = "*cough* 我希望 #Ilisa 能快点把药制好...";
	static final String TAD_TALK_COMPLETE_QUEST = "谢谢！我会尽快和 #Ilisa 谈谈。";

	static final String TAD_TALK_ASK_FOR_EMPTY_FLASK = "我感觉很不好... 我需要拿到一瓶药济。你能帮我取一个空的 #flask 吗?";
	static final String TAD_TALK_ALREADY_HELPED_1 = "我现在好了，谢谢。";
	static final String TAD_TALK_ALREADY_HELPED_2 = "你帮我脱离险境！现在我觉得好多了！";
	static final String TAD_TALK_WAIT_FOR_FLASK = "*cough* Oh 亲爱的... 我真的需要这些药！请快点去找 #Margaretrry 取个 #flask 回来.";
	static final String TAD_TALK_FLASK_MARGARET = "你应该去 #Margaret 处取个 flask.";
	static final String TAD_TALK_INTRODUCE_MARGARET = "Margaret 是酒店的服务员，平时就在离这不远的酒店里。";
	static final String TAD_TALK_CONFIRM_QUEST = "所以, 你会帮我吗?";
	static final String TAD_TALK_QUEST_REFUSED = "Oh, 请不要改变主意好吗？ *sneeze*";
	static final String TAD_TALK_QUEST_ACCEPTED = "太好了! 请尽快吧. *sneeze*";

	static final String HISTORY_MET_TAD = "我在 Semos 旅店里见到了 Ted.";
	static final String HISTORY_QUEST_OFFERED = "他让我去 Semos 酒店里 找 Margaret 买一个 flask .";
	static final String HISTORY_GOT_FLASK = "我拿到了 flask ，并马上带给了 Tad .";
	static final String HISTORY_TAKE_FLASK_TO_ILISA = "Tad 让我拿着 flask 到 Semos教堂找 Ilisa.";
	static final String HISTORY_ILISA_ASKED_FOR_HERB = "Ilisa 让我去 Semos 镇的北边取一种叫 Arandula 的药草, 这种药草长在小树林的旁边.";
	static final String HISTORY_GOT_HERB = "我找到了 Arandula 药草，并把它带给了 Ilisa.";
	static final String HISTORY_POTION_READY = "Ilisa 制作了一种强心济帮助 Tad 治疗。她让我给他带话，药已备好。";
	static final String HISTORY_DONE = "Tad 对我十分感谢.";

	static final String STATE_START = "start";
	static final String STATE_ILISA = "ilisa";
	static final String STATE_HERB = "corpse&herbs";
	static final String STATE_SHOWN_DRAWING = "shownDrawing";
	static final String STATE_POTION = "potion";
	static final String STATE_DONE = "done";

	private static final String QUEST_SLOT = "introduce_players";

	@Override
	public String getSlotName() {
		return QUEST_SLOT;
	}

	@Override
	public List<String> getHistory(final Player player) {
		final List<String> res = new ArrayList<String>();
		if (player.hasQuest("TadFirstChat")) {
			res.add(HISTORY_MET_TAD);
		}
		if (!player.hasQuest(QUEST_SLOT)) {
			return res;
		}
		final String questState = player.getQuest(QUEST_SLOT, 0);
		if (player.isQuestInState(QUEST_SLOT, 0, STATE_START, STATE_ILISA, STATE_HERB, STATE_POTION, STATE_DONE)) {
			res.add(HISTORY_QUEST_OFFERED);
		}
		if (questState.equals(STATE_START) && player.isEquipped("flask")
				|| player.isQuestInState(QUEST_SLOT, 0, STATE_ILISA, STATE_HERB, STATE_POTION, STATE_DONE)) {
			res.add(HISTORY_GOT_FLASK);
		}
		if (player.isQuestInState(QUEST_SLOT, 0, STATE_ILISA, STATE_HERB, STATE_POTION, STATE_DONE)) {
			res.add(HISTORY_TAKE_FLASK_TO_ILISA);
		}
		if (player.isQuestInState(QUEST_SLOT, 0, STATE_HERB, STATE_POTION, STATE_DONE)) {
			res.add(HISTORY_ILISA_ASKED_FOR_HERB);
		}
		if (questState.equals(STATE_HERB) && player.isEquipped("arandula")
				|| player.isQuestInState(QUEST_SLOT, 0, STATE_POTION, STATE_DONE)) {
			res.add(HISTORY_GOT_HERB);
		}
		if (player.isQuestInState(QUEST_SLOT, 0, STATE_POTION, STATE_DONE)) {
			res.add(HISTORY_POTION_READY);
		}
		if (questState.equals(STATE_DONE)) {
			res.add(HISTORY_DONE);
		}
		return res;
	}

	private void step_1() {
		final SpeakerNPC npc = npcs.get("Tad");
		npc.add(ConversationStates.ATTENDING,
				ConversationPhrases.QUEST_MESSAGES,
				new QuestCompletedCondition(QUEST_SLOT),
				ConversationStates.ATTENDING,
				TAD_TALK_ALREADY_HELPED_1,
				null);

		npc.add(ConversationStates.ATTENDING,
				ConversationPhrases.QUEST_MESSAGES,
				new QuestNotStartedCondition(QUEST_SLOT),
				ConversationStates.QUEST_OFFERED,
				TAD_TALK_ASK_FOR_EMPTY_FLASK,
				null);

		// In case Quest has already been completed
		npc.add(ConversationStates.ATTENDING,
				"flask",
				new QuestCompletedCondition(QUEST_SLOT),
				ConversationStates.ATTENDING,
				TAD_TALK_ALREADY_HELPED_2,
				null);

		// If quest is not started yet, start it.
		npc.add(ConversationStates.QUEST_OFFERED,
				"flask",
				new QuestNotStartedCondition(QUEST_SLOT),
				ConversationStates.QUEST_OFFERED,
				TAD_TALK_FLASK_MARGARET,
				null);

		npc.add(ConversationStates.QUEST_OFFERED,
				ConversationPhrases.YES_MESSAGES,
				null,
				ConversationStates.ATTENDING,
				TAD_TALK_QUEST_ACCEPTED,
				new SetQuestAction(QUEST_SLOT, 0, STATE_START));

		npc.add(ConversationStates.QUEST_OFFERED,
				ConversationPhrases.NO_MESSAGES,
				null,
				ConversationStates.ATTENDING,
				TAD_TALK_QUEST_REFUSED,
				null);

		npc.add(ConversationStates.QUEST_OFFERED,
				"margaret",
				null,
				ConversationStates.QUEST_OFFERED,
				TAD_TALK_INTRODUCE_MARGARET + " " + TAD_TALK_CONFIRM_QUEST,
				null);

		// Remind player about the quest
		npc.add(ConversationStates.ATTENDING,
				"flask",
				new AndCondition(
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_START),
						new NotCondition(new PlayerHasItemWithHimCondition("flask"))),
				ConversationStates.ATTENDING,
				TAD_TALK_WAIT_FOR_FLASK,
				null);

        // Remind player about the quest
        npc.add(ConversationStates.ATTENDING,
                ConversationPhrases.QUEST_MESSAGES,
                new QuestInStateCondition(QUEST_SLOT, 0, STATE_START),
                ConversationStates.ATTENDING,
                TAD_TALK_WAIT_FOR_FLASK,
                null);

		npc.add(ConversationStates.ATTENDING,
				"margaret",
				null,
				ConversationStates.ATTENDING,
				TAD_TALK_INTRODUCE_MARGARET,
				null);
	}

	private void step_2() {
		/** Just buy the stuff from Margaret. It isn't a quest */
	}

	private void step_3() {
		final SpeakerNPC npc = npcs.get("Tad");

		final List<ChatAction> processStep = new LinkedList<ChatAction>();
		processStep.add(new EquipItemAction("money", 10));
		processStep.add(new IncreaseXPAction(10));
		processStep.add(new SetQuestAction(QUEST_SLOT, 0, STATE_ILISA));

		// starting the conversation the first time after getting a flask.
		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(new GreetingMatchesNameCondition(npc.getName()),
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_START),
						new PlayerHasItemWithHimCondition("flask")),
				ConversationStates.ATTENDING,
				TAD_TALK_GOT_FLASK + " " + TAD_TALK_REWARD_MONEY + " " + TAD_TALK_FLASK_ILISA,
				new MultipleActions(processStep));

		// player said hi with flask on ground then picked it up and said flask
		npc.add(ConversationStates.ATTENDING, "flask",
                new AndCondition(new QuestInStateCondition(QUEST_SLOT, 0, STATE_START), new PlayerHasItemWithHimCondition("flask")),
                ConversationStates.ATTENDING,
                TAD_TALK_GOT_FLASK + " " + TAD_TALK_REWARD_MONEY + " " + TAD_TALK_FLASK_ILISA,
                new MultipleActions(processStep));

		// remind the player to take the flask to Ilisa.
		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(new GreetingMatchesNameCondition(npc.getName()),
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_ILISA),
						new PlayerHasItemWithHimCondition("flask")),
				ConversationStates.ATTENDING,
				TAD_TALK_GOT_FLASK + " " + TAD_TALK_FLASK_ILISA,
				null);

		// another reminder in case player says task again
        npc.add(ConversationStates.ATTENDING, ConversationPhrases.QUEST_MESSAGES,
                new QuestInStateCondition(QUEST_SLOT, 0, STATE_ILISA),
                ConversationStates.ATTENDING,
                TAD_TALK_REMIND_FLASK_ILISA,
                null);

		npc.add(ConversationStates.ATTENDING,
				Arrays.asList("ilisa", "iiisa", "llisa"),
				null,
				ConversationStates.ATTENDING,
				TAD_TALK_INTRODUCE_ILISA,
				null);
	}

	private void step_4() {
		final SpeakerNPC npc = npcs.get("Ilisa");

		npc.add(ConversationStates.IDLE,
				ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_ILISA),
						new NotCondition(new PlayerHasItemWithHimCondition("flask"))),
				ConversationStates.ATTENDING,
				ILISA_TALK_ASK_FOR_FLASK,
				null);

		final List<ChatAction> processStep = new LinkedList<ChatAction>();
		processStep.add(new DropItemAction("flask"));
		processStep.add(new IncreaseXPAction(10));
		processStep.add(new SetQuestAction(QUEST_SLOT, 0, STATE_HERB));

		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_ILISA),
						new PlayerHasItemWithHimCondition("flask")),
				ConversationStates.ATTENDING,
				ILISA_TALK_ASK_FOR_HERB,
				new MultipleActions(processStep));

		ChatAction showArandulaDrawing = new ExamineChatAction("arandula.png", "Ilisa's drawing", "Arandula");
		ChatAction flagDrawingWasShown = new SetQuestAction(QUEST_SLOT, 1, STATE_SHOWN_DRAWING);
		npc.add(
				ConversationStates.ATTENDING,
				Arrays.asList("yes", "ok"),
				new AndCondition(
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_HERB),
						new NotCondition(new QuestInStateCondition(QUEST_SLOT, 1, STATE_SHOWN_DRAWING)),
						new NotCondition(new PlayerHasItemWithHimCondition("arandula"))),
				ConversationStates.ATTENDING,
				ILISA_TALK_DESCRIBE_HERB,
				new MultipleActions(showArandulaDrawing, flagDrawingWasShown));

		npc.add(
				ConversationStates.ATTENDING,
				Arrays.asList("herb", "arandula"),
				new QuestStartedCondition(QUEST_SLOT),
				ConversationStates.ATTENDING,
				ILISA_TALK_DESCRIBE_HERB,
				new MultipleActions(showArandulaDrawing, flagDrawingWasShown));

		npc.add(
				ConversationStates.ATTENDING,
				"tad",
				null,
				ConversationStates.ATTENDING,
				ILISA_TALK_INTRODUCE_TAD,
				null);
	}

	private void step_5() {
		final SpeakerNPC npc = npcs.get("Ilisa");

		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_HERB),
						new NotCondition(new PlayerHasItemWithHimCondition("arandula"))),
				ConversationStates.ATTENDING,
				ILISA_TALK_REMIND_HERB, null);

		final List<ChatAction> processStep = new LinkedList<ChatAction>();
		processStep.add(new DropItemAction("arandula"));
		processStep.add(new IncreaseXPAction(50));
        processStep.add(new IncreaseKarmaAction(4));
		processStep.add(new SetQuestAction(QUEST_SLOT, 0, STATE_POTION));

		npc.add(ConversationStates.IDLE, ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_HERB),
						new PlayerHasItemWithHimCondition("arandula")),
				ConversationStates.ATTENDING,
				ILISA_TALK_PREPARE_MEDICINE,
				new MultipleActions(processStep));

		npc.add(ConversationStates.ATTENDING, Arrays.asList(STATE_POTION,
				"medicine"), null, ConversationStates.ATTENDING,
				ILISA_TALK_EXPLAIN_MEDICINE, null);
	}

	private void step_6() {
		SpeakerNPC npc = npcs.get("Tad");

        // another reminder in case player says task again
        npc.add(ConversationStates.ATTENDING,
        		ConversationPhrases.QUEST_MESSAGES,
                new QuestInStateCondition(QUEST_SLOT, 0, STATE_HERB),
                ConversationStates.ATTENDING,
                TAD_TALK_REMIND_MEDICINE,
                null);

		final List<ChatAction> processStep = new LinkedList<ChatAction>();
		processStep.add(new IncreaseXPAction(200));
		processStep.add(new SetQuestAction(QUEST_SLOT, 0, STATE_DONE));

		npc.add(ConversationStates.IDLE,
				ConversationPhrases.GREETING_MESSAGES,
				new AndCondition(
						new GreetingMatchesNameCondition(npc.getName()),
						new QuestInStateCondition(QUEST_SLOT, 0, STATE_POTION)),
				ConversationStates.ATTENDING,
				TAD_TALK_COMPLETE_QUEST,
				new MultipleActions(processStep));

		/*
		 * if player has not finished this quest, ketteh will remind player about him.
		 * if player has not started, and not finished, ketteh will ask if player has met him.
		 */
		npc = npcs.get("Ketteh Wehoh");

        npc.add(ConversationStates.ATTENDING,
        		ConversationPhrases.GOODBYE_MESSAGES,
        		new AndCondition(
        				new QuestStartedCondition(QUEST_SLOT),
        				new QuestNotCompletedCondition(QUEST_SLOT)),
                ConversationStates.IDLE,
                KETTEH_TALK_BYE_REMINDS_OF_TAD,
                null);

        npc.add(ConversationStates.ATTENDING,
        		ConversationPhrases.GOODBYE_MESSAGES,
        		new QuestNotStartedCondition(QUEST_SLOT),
                ConversationStates.IDLE,
                KETTEH_TALK_BYE_INTRODUCES_TAD,
                null);

	}

	@Override
	public void addToWorld() {
		fillQuestInfo(
				"Tad的药济",
				"Tad, Semos 旅馆的一位青年，需要我帮忙取些药。",
				false);
		step_1();
		step_2();
		step_3();
		step_4();
		step_5();
		step_6();
	}
	@Override
	public String getName() {
		return "MedicineForTad";
	}

	@Override
	public String getRegion() {
		return Region.SEMOS_CITY;
	}
	@Override
	public String getNPCName() {
		return "Tad";
	}
}
