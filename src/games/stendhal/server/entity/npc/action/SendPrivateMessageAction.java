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

import games.stendhal.common.NotificationType;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.player.Player;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Sends the message as a private text 
 * Optional notification type when used with NPCs
 */
public class SendPrivateMessageAction implements ChatAction {

	private final String text;
	private final NotificationType type;

	/**
	 * Creates a new SendPrivateMessageAction.
	 * 
	 * @param text text to send
	 */
	public SendPrivateMessageAction(String text) {
		this.text = text;
		this.type = NotificationType.PRIVMSG;
	}
	
	/**
	 * Creates a new SendPrivateMessageAction - does NOT work with portals
	 * 
	 * @param text text to send
	 * 
	 * @param type type of message
	 */
	public SendPrivateMessageAction(final NotificationType type, final String text) {
		this.text = text;
		this.type = type;
	}

	public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
		player.sendPrivateText(type, text);
	}

	@Override
	public String toString() {
		// would need to send the type toString also to include the type here
		return "Send Private Message: " + text;
	}


	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, false,
				SendPrivateMessageAction.class);
	}
}
