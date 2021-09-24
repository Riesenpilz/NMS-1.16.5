package io.github.riesenpilz.nms.packet.playOut;

import java.util.List;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateAttributes;
import net.minecraft.server.v1_16_R3.PacketPlayOutUpdateAttributes.AttributeSnapshot;

/**
 * https://wiki.vg/Protocol#Entity_Properties
 * <p>
 * Sets attributes on the given entity.
 * <p>
 * Packet ID: 0x63<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutEntityPropertiesEvent extends PacketPlayOutEvent {

	private int entityId;
    private List<AttributeSnapshot> attributes;

	
	@SuppressWarnings("unchecked")
	public PacketPlayOutEntityPropertiesEvent(Player injectedPlayer, PacketPlayOutUpdateAttributes packet) {
		super(injectedPlayer);
		
		entityId = Field.get(packet, "a", int.class);
		attributes = Field.get(packet, "b", List.class);
	}

	public PacketPlayOutEntityPropertiesEvent(Player injectedPlayer, int entityId, List<AttributeSnapshot> attributes) {
		super(injectedPlayer);
		this.entityId = entityId;
		this.attributes = attributes;
	}

	public int getEntityId() {
		return entityId;
	}

	public List<AttributeSnapshot> getAttributes() {
		return attributes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		final PacketPlayOutUpdateAttributes packet = new PacketPlayOutUpdateAttributes();
		Field.set(packet, "a", entityId);
		Field.get(packet, "b", List.class).addAll(attributes);
		return packet;
	}

	@Override
	public int getPacketID() {
		return 0x63;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Entity_Properties";
	}
}
