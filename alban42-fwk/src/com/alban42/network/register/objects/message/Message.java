/**
 *
 */
package com.alban42.network.register.objects.message;

import com.alban42.network.register.objects.packet.Packet;

/**
 * @author Alban
 */
public class Message extends Packet {

	private String message;

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(final String message) {
		this.message = message;
	}
}
