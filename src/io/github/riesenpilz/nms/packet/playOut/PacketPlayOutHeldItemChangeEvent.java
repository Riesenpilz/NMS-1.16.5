package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutHeldItemSlot;

/**
 * https://wiki.vg/Protocol#Held_Item_Change_.28clientbound.29
 * <p>
 * Sent to change the player's slot selection.
 * <p>
 * Packet ID: 0x48<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutHeldItemChangeEvent extends PacketPlayOutEvent {

	/**
	 * The slot which the player has selected (0�8).
	 */
	private byte slot;

	public PacketPlayOutHeldItemChangeEvent(Player injectedPlayer, PacketPlayOutHeldItemSlot packet) {
		super(injectedPlayer);

		slot = Field.get(packet, "a", byte.class);
	}

	public PacketPlayOutHeldItemChangeEvent(Player injectedPlayer, byte slot) {
		super(injectedPlayer);
		this.slot = slot;
	}

	public byte getSlot() {
		return slot;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutHeldItemSlot(slot);
	}

	@Override
	public int getPacketID() {
		return 0x48;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Held_Item_Change_.28clientbound.29";
	}
}
