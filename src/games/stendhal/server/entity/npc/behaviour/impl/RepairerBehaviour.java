/***************************************************************************
 *                   (C) Copyright 2016 - Faiumoni e. V.                   *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.npc.behaviour.impl;

import java.util.List;
import java.util.Set;

import games.stendhal.common.grammar.ItemParserResult;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.behaviour.impl.prices.PriceCalculationStrategy;
import games.stendhal.server.entity.player.Player;

/**
 * Behaviour for NPCs repairing items
 *
 * @author madmetzger
 */
public class RepairerBehaviour extends TransactionBehaviour {

	private final PriceCalculationStrategy priceCalculator;

	/**
	 * Create a new RepairerBehaviour with a given price calculation strategy
	 *
	 * @param calculator the price calculator
	 * @param items the items that can be repaired
	 */
	public RepairerBehaviour(PriceCalculationStrategy calculator, Set<String> items) {
		super(items);
		priceCalculator = calculator;
	}

	@Override
	public boolean transactAgreedDeal(ItemParserResult res, EventRaiser seller,
			Player player) {
		List<Item> equipped = player.getAllEquipped(res.getChosenItemName());
		if(!equipped.isEmpty()) {
			boolean foundMoreThanOne = false;
			//found more that one item, important for answer of NPC
			if(equipped.size() > 1) {
				foundMoreThanOne = true;
			}
			//choose the first item as reference
			Item toRepair = equipped.iterator().next();
			//select the most damaged item from the found items
			for(Item i : equipped) {
				if(i.getDeterioration() > toRepair.getDeterioration()) {
					toRepair = i;
				}
			}
			// check if item is damaged
			if(toRepair.getDeterioration() > 0) {
				int price = priceCalculator.calculatePrice(toRepair, player);
				//only repair if player can afford it
				if (player.isEquipped("money", price)) {
					toRepair.repair();
					player.drop("money", price);
					//tell player about result of repairing as for more than one found item the most damaged one is repaired
					if(foundMoreThanOne) {
						seller.say("你带拿了不只一个 "+res.getChosenItemName()+" with you. 所我只能修了破坏最严重的那个.");
					} else {
						seller.say("我修好了你的 "+res.getChosenItemName());
					}
					return true;
				} else {
					seller.say("修好 "+res.getChosenItemName()+" 的价格你付不起啊");
					return false;
				}
			} else {
				seller.say("你的 "+res.getChosenItemName()+" 没有损坏.");
				return false;
			}
		}
		seller.say("你身上没有 "+res.getChosenItemName()+" 呀.");
		return false;
	}

	@Override
	public ChatCondition getTransactionCondition() {
		return new ChatCondition() {
			@Override
			public boolean fire(Player player, Sentence sentence, Entity npc) {
				ItemParserResult res = parse(sentence);
				return canDealWith(res.getChosenItemName());
			}
		};
	}

	@Override
	public ChatAction getRejectedTransactionAction() {
		return new SayTextAction("抱歉， [name], 我不能修好你的东西.");
	}

	/**
	 * Calculate the price for the given item
	 * @param item the item to repair
	 * @param player the player wanting to repair
	 * @return the price for the player
	 */
	public int getPrice(String item, Player player) {
		return priceCalculator.calculatePrice(item, player);
	}

	/**
	 * Check if this NPC can repair this item
	 *
	 * @param chosen the item to repair
	 * @return true iff this NPC is able to repair the item
	 */
	public boolean canDealWith(String chosen) {
		if(!getItemNames().isEmpty()) {
			return getItemNames().contains(chosen);
		}
		return true;
	}

}
