/**
 *
 */
package com.alban42.network.register;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alban42.network.register.objects.message.Message;
import com.alban42.network.register.objects.packet.Packet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * @author Alban
 */
public class ClassRegister {

	// This registers objects that are going to be sent over the network.
	static public void register(final EndPoint endPoint) {
		final Kryo kryo = endPoint.getKryo();
		// Standard classes
		kryo.register(UUID.class);
		kryo.register(ArrayList.class);
		kryo.register(List.class);
		kryo.register(String.class);

		// Special classes
		kryo.register(Packet.class);
		kryo.register(Message.class);

	}
}
