/**
 *
 */
package com.alban42.network.register.objects;

import com.alban42.network.register.objects.packet.Packet;

/**
 * @author Alban
 */
public interface ICaller {

	public void responseReceived(Packet response);

}
