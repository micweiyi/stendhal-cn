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
package games.stendhal.server.entity.npc.condition;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * An inverse condition.
 */
public class NotCondition implements ChatCondition {

	private final ChatCondition condition;

	/**
	 * Creates a new "not"-condition.
	 * 
	 * @param condition
	 *            condition which result is to be inversed
	 */
	public NotCondition(final ChatCondition condition) {
		this.condition = condition;
	}

	public boolean fire(final Player player, final Sentence sentence, final Entity entity) {
		return !condition.fire(player, sentence, entity);
	}

	@Override
	public String toString() {
		return "NOT <" + condition + ">";
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false,
				NotCondition.class);
	}
}
