/***************************************************************************
 *                   (C) Copyright 2003-2011 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.core.rp.achievement.factory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import games.stendhal.server.core.rp.achievement.Achievement;
import games.stendhal.server.core.rp.achievement.Category;
import games.stendhal.server.entity.npc.condition.PlayerLootedNumberOfItemsCondition;

/**
 * Factory for item related achievements.
 *
 * @author madmetzger
 */
public class ItemAchievementFactory extends AbstractAchievementFactory {

	@Override
	protected Category getCategory() {
		return Category.ITEM;
	}

	@Override
	public Collection<Achievement> createAchievements() {
		List<Achievement> itemAchievements = new LinkedList<Achievement>();

		itemAchievements.add(createAchievement("item.money.100", "First pocket money", "Loot 100 money from creatures",
				Achievement.EASY_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(100, "money")));

		itemAchievements.add(createAchievement("item.money.1000000", "You don't need it anymore", "Loot 1000000 money from creatures",
				Achievement.HARD_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1000000, "money")));

		itemAchievements.add(createAchievement("item.set.red", "Amazon's Menace", "Loot a complete red equipment set",
				Achievement.MEDIUM_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "红铠甲", "red helmet", "红斗篷", "red legs", "红靴子",
						"red shield")));

		itemAchievements.add(createAchievement("item.set.blue", "Feeling Blue", "Loot a complete blue equipment set",
				Achievement.MEDIUM_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "蓝色盔甲", "blue helmet", "蓝色条纹斗篷", "blue legs",
						"蓝靴子", "blue shield")));

		itemAchievements.add(createAchievement("item.set.elvish", "Nalwor's Bane", "Loot a complete elvish equipment set",
				Achievement.MEDIUM_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "精灵护甲", "elvish hat", "像小精灵的斗篷", "elvish legs",
						"精灵靴", "elvish shield")));

		itemAchievements.add(createAchievement("item.set.shadow", "Shadow Dweller", "Loot a complete shadow equipment set",
				Achievement.MEDIUM_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "影子铠甲", "shadow helmet", "影子斗篷", "shadow legs",
						"影子靴子", "shadow shield")));

		itemAchievements.add(createAchievement("item.set.chaos", "Chaotic Looter", "Loot a complete chaos equipment set",
				Achievement.HARD_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "浑沌护甲", "chaos helmet", "混沌斗篷", "chaos legs",
						"混沌靴", "chaos shield")));

		itemAchievements.add(createAchievement("item.set.golden", "Golden Boy", "Loot a complete golden equipment set",
				Achievement.MEDIUM_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "黄金铠甲", "golden helmet", "金斗篷", "golden legs",
						"金靴子", "golden shield")));

		itemAchievements.add(createAchievement("item.set.black", "Come to the dark side", "Loot a complete black equipment set",
				Achievement.HARD_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "黑色盔甲", "black helmet", "黑斗篷", "black legs",
						"黑靴子", "black shield")));

		itemAchievements.add(createAchievement("item.set.mainio", "Excellent Stuff", "Loot a complete mainio equipment set",
				Achievement.HARD_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "奇妙甲", "mainio helmet", "华丽的斗篷", "mainio legs",
						"很棒的靴子", "mainio shield")));

		itemAchievements.add(createAchievement("item.set.xeno", "A Bit Xenophobic?", "Loot a complete xeno equipment set",
				Achievement.HARD_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "异种元素甲", "xeno helmet", "异种元素斗篷", "xeno legs",
						"异种元素靴子", "xeno shield")));

		itemAchievements.add(createAchievement("item.cloak.dragon", "Dragon Slayer", "Loot all dragon cloaks",
				Achievement.MEDIUM_BASE_SCORE, true,
				new PlayerLootedNumberOfItemsCondition(1, "黑龙斗篷", "蓝龙斗篷", "骨龙斗篷",
						"绿龙斗篷", "红龙斗篷")));

		return itemAchievements;
	}

}
