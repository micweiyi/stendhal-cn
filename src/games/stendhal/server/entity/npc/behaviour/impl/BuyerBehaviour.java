/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
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

import java.util.Map;

//import games.stendhal.common.grammar.Grammar;
import games.stendhal.common.grammar.ItemParserResult;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.StackableItem;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

/**
 * Represents the behaviour of a NPC who is able to buy items from a player.
 */
public class BuyerBehaviour extends MerchantBehaviour {

	public BuyerBehaviour(final Map<String, Integer> priceList) {
		super(priceList);
	}

	/**
	 * Gives the money for the deal to the player. If the player can't carry the
	 * money, puts it on the ground.
	 *
	 * @param res
	 *
	 * @param player
	 *            The player who sells
	 */
	protected void payPlayer(ItemParserResult res, final Player player) {
		final StackableItem money = (StackableItem) SingletonRepository.getEntityManager().getItem("money");
		money.setQuantity(getCharge(res, player));
		player.equipOrPutOnGround(money);
	}

	/**
	 * Transacts the deal that is described in BehaviourResult.
	 *
	 * @param seller
	 *            The NPC who buys
	 * @param player
	 *            The player who sells
	 * @return true iff the transaction was successful, that is when the player
	 *         has the item(s).
	 */
	@Override
	public boolean transactAgreedDeal(ItemParserResult res, final EventRaiser seller, final Player player) {
		if (player.drop(res.getChosenItemName(), res.getAmount())) {
			payPlayer(res, player);
			seller.say("谢谢！这是你的钱.");
			player.incSoldForItem(res.getChosenItemName(), res.getAmount());
			return true;
		} else {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("抱歉！你没有 ");
			stringBuilder.append("任何");

			stringBuilder.append(" ");
			stringBuilder.append(res.getAmount()+  res.getChosenItemName());
			stringBuilder.append(".");
			seller.say(stringBuilder.toString());
			return false;
		}
	}
}
