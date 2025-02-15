package io.github.riesenpilz.nmsUtilities.packet.playOut;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nmsUtilities.packet.HasBlockPosition;
import io.github.riesenpilz.nmsUtilities.packet.PacketUtils;
import io.github.riesenpilz.nmsUtilities.reflections.Field;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockBreakAnimation;

/**
 * https://wiki.vg/Protocol#Block_Break_Animation
 * <p>
 * 0�9 are the displayable destroy stages and each other number means that there
 * is no animation on this coordinate.
 * <p>
 * Block break animations can still be applied on air; the animation will remain
 * visible although there is no block being broken. However, if this is applied
 * to a transparent block, odd graphical effects may happen, including water
 * losing its transparency. (An effect similar to this can be seen in normal
 * gameplay when breaking ice blocks)
 * <p>
 * If you need to display several break animations at the same time you have to
 * give each of them a unique Entity ID. The entity ID does not need to
 * correspond to an actual entity on the client. It is valid to use a randomly
 * generated number.
 * <p>
 * Packet ID: 0x08<br>
 * State: Play<br>
 * Bound To: Client
 *
 * @author Martin
 *
 */
public class PacketPlayOutBlockBreakAnimationEvent extends PacketPlayOutEvent implements HasBlockPosition {

	private int entityId;
	private Location blockLocation;

	/**
	 * 0 - 9
	 */
	private int destroyStage;

	public PacketPlayOutBlockBreakAnimationEvent(Player injectedPlayer, PacketPlayOutBlockBreakAnimation packet) {
		super(injectedPlayer);
		
		Validate.notNull(packet);
		
		entityId = Field.get(packet, "a", int.class);
		blockLocation = PacketUtils.toLocation(Field.get(packet, "b", BlockPosition.class), injectedPlayer.getWorld());
		destroyStage = Field.get(packet, "c", int.class);

	}

	public PacketPlayOutBlockBreakAnimationEvent(Player injectedPlayer, int entityId, Location blockLocation,
			int destroyStage) {
		super(injectedPlayer);
		
		Validate.notNull(blockLocation);
		
		this.entityId = entityId;
		this.blockLocation = blockLocation;
		this.destroyStage = destroyStage;
	}

	public int getEntityId() {
		return entityId;
	}

	@Override
	public Location getBlockLocation() {
		return blockLocation;
	}

	public int getDestroyStage() {
		return destroyStage;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutBlockBreakAnimation(entityId, PacketUtils.toBlockPosition(blockLocation), destroyStage);
	}

	@Override
	public int getPacketID() {
		return 0x08;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Block_Break_Animation";
	}
}
