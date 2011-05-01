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
package games.stendhal.server.entity.npc.action;

import java.util.Calendar;

import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Sets the state of a quest to the current year.
 */
public class SetQuestToYearAction implements ChatAction {

	private final String questname;
	private final int index;

	/**
	 * Creates a new SetQuestToYearAction.
	 * 
	 * @param questname name of quest-slot to change
	 */
	public SetQuestToYearAction(final String questname) {
		this.questname = questname;
		this.index = -1;
	}

	/**
	 * Creates a new SetQuestToYearAction.
	 * 
	 * @param questname name of quest-slot to change
	 * @param index index of sub state
	 */
	public SetQuestToYearAction(final String questname, final int index) {
		this.questname = questname;
		this.index = index;
	}

	public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
		String state = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		if (index > -1) {
			player.setQuest(questname, index, state);
		} else {
			player.setQuest(questname, state);
		}
	}

	@Override
	public String toString() {
		return "SetQuestToYearAction<" + questname + "[" + index + "]>";
	}


	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false,
				SetQuestToYearAction.class);
	}
}
