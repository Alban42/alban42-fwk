/**
 *
 */
package com.alban42.network.register;

import com.alban42.network.register.objects.message.Message;
import com.alban42.network.register.objects.packet.Packet;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Alban
 */
public abstract class ClassRegister {

    // This registers objects that are going to be sent over the network.
    public void register(final EndPoint endPoint) {
        final Kryo kryo = endPoint.getKryo();
        // Standard classes
        kryo.register(UUID.class);
        kryo.register(ArrayList.class);
        kryo.register(List.class);
        kryo.register(String.class);

        // Special classes
        kryo.register(Packet.class);
        kryo.register(Message.class);

        // User specific classes
        if (registerSpecificClasses() != null && !registerSpecificClasses().isEmpty()) {
            registerSpecificClasses().stream().forEach(x -> kryo.register(x));
        }
    }

    /**
     * Add here specifics classes of the objects you need to send over the network.
     *
     * @return the list of the classes to be registered.
     */
    public abstract List<Class> registerSpecificClasses();
}
