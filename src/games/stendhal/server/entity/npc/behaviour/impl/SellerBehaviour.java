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

package games.stendhal.server.entity.npc.behaviour.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

//import games.stendhal.common.grammar.Grammar;
import games.stendhal.common.grammar.ItemParserResult;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.Item;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

/**
 * Represents the behaviour of a NPC who is able to sell items to a player.
 */
public class SellerBehaviour extends MerchantBehaviour {

	/** the logger instance. */
	private static final Logger logger = Logger.getLogger(SellerBehaviour.class);

	/** the factor extra that player killers pay for items. should be > 1 always */
	public static final double BAD_BOY_BUYING_PENALTY = 1.5;

	/**
	 * Creates a new SellerBehaviour with an empty pricelist.
	 */
	public SellerBehaviour() {
		super(new HashMap<String, Integer>());
	}

	/**
	 * Creates a new SellerBehaviour with a pricelist.
	 *
	 * @param priceList
	 *            list of item names and their prices
	 */
	public SellerBehaviour(final Map<String, Integer> priceList) {
		super(priceList);
	}

	/**
	 * Transacts the sale that has been agreed on earlier via setChosenItem()
	 * and setAmount().
	 *
	 * @param seller
	 *            The NPC who sells
	 * @param player
	 *            The player who buys
	 * @return true iff the transaction was successful, that is when the player
	 *         was able to equip the item(s).
	 */
	@Override
	public boolean transactAgreedDeal(ItemParserResult res, final EventRaiser seller, final Player player) {
		String chosenItemName = res.getChosenItemName();
		int amount = res.getAmount();

		final Item item = getAskedItem(chosenItemName);
		if (item == null) {
			logger.error("尝试卖一件不存在的物品: " + chosenItemName);
			return false;
		}

		// set amount of stackable items
		if (item instanceof StackableItem) {
			((StackableItem) item).setQuantity(amount);
		} else if (amount > 1) {
			// Fixing user input with more than one item is already handled in SellerAdder, so this should not happen here at all.
			logger.error("Trying to sell more than one " + chosenItemName + " in one transaction.");
		}

		if (amount <= 0) {
			seller.say("抱歉，你至少要买一件吧。");
			return false;
		}

		int price = getCharge(res, player);
		if (player.isBadBoy()) {
			price = (int) (BAD_BOY_BUYING_PENALTY * price);
		}

		if (player.isEquipped("money", price)) {
			if (player.equipToInventoryOnly(item)) {
				player.drop("money", price);
				seller.say("谢谢惠顾! 这 "
						+ amount + " 个 "
						+ chosenItemName + " 请拿好!");
				player.incBoughtForItem(chosenItemName, amount);
				return true;
			} else {
				seller.say("抱歉，但你不能装备 "
						+  chosenItemName + ".");
				return false;
			}
		} else {
			seller.say("抱歉，你的钱不够!");
			return false;
		}
	}

	public Item getAskedItem(final String askedItem) {
		final Item item = SingletonRepository.getEntityManager().getItem(askedItem);
		return item;
	}
}
