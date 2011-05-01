/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2010-2011 - Stendhal                    *
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

import java.util.Arrays;
import java.util.List;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.npc.ChatCondition;
import games.stendhal.server.entity.player.Player;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Checks if an optional subject in the user input matches the NPC name.
 */
public class GreetingMatchesNameCondition implements ChatCondition {

	private final String name;
	private final List<String> nameWords;

	/**
	 * Creates a new GreetingMatchesNameCondition.
	 * 
	 * @param name
	 *            name to match against
	 */
	public GreetingMatchesNameCondition(final String name) {
		this.name = name;
		nameWords = Arrays.asList(name.toLowerCase().split(" "));
	}

	public boolean fire(final Player player, final Sentence sentence, final Entity entity) {
		if (sentence == null) {
			return true;
		}

		String subjectName = sentence.getSubjectName();
		if (subjectName == null) {
			return true;
		}

		// Does the name match completely?
		if (subjectName.equalsIgnoreCase(name)) {
			return true;
		}

		// Does a sub-sequence of words match the name?
		String[] subjectWords = subjectName.split(" ");
		if (matchesName(subjectWords)) {
			return true;
		}

		return false;
	}

	/**
	 * Check if all of the given words are present in nameWords. 
	 * @param words list of words to search for
	 * @return
	 */
	private boolean matchesName(String... words) {
		for(String word : words) {
			if (!nameWords.contains(word)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return "hi <" + name + ">";
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false,
				GreetingMatchesNameCondition.class);
	}
}
