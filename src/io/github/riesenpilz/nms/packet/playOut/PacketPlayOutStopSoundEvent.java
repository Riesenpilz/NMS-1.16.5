package io.github.riesenpilz.nms.packet.playOut;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import io.github.riesenpilz.nms.packet.PacketUtils;
import io.github.riesenpilz.nms.reflections.Field;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.Packet;
import net.minecraft.server.v1_16_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_16_R3.PacketPlayOutStopSound;

/**
 * https://wiki.vg/Protocol#Stop_Sound
 * <p>
 * Packet ID: 0x5D<br>
 * State: Play<br>
 * Bound To: Client
 * 
 * @author Martin
 *
 */
public class PacketPlayOutStopSoundEvent extends PacketPlayOutEvent {

	/**
	 * If not present, then all sounds are cleared.
	 */
	private Sound sound;

	/**
	 * If not present, then sounds from all sources are cleared.
	 */
	private SoundCategory category;

	public PacketPlayOutStopSoundEvent(Player injectedPlayer, PacketPlayOutStopSound packet) {
		super(injectedPlayer);

		sound = getSound(Field.get(packet, "a", MinecraftKey.class).getKey());
		category = SoundCategory
				.valueOf(Field.get(packet, "b", net.minecraft.server.v1_16_R3.SoundCategory.class).name());
	}

	public PacketPlayOutStopSoundEvent(Player injectedPlayer, Sound sound, SoundCategory category) {
		super(injectedPlayer);
		this.sound = sound;
		this.category = category;
	}

	public Sound getSound() {
		return sound;
	}

	public SoundCategory getCategory() {
		return category;
	}

	private Sound getSound(String key) {
		for (Sound sound : Sound.values())
			if (sound.getKey().getKey().equals(key))
				return sound;
		return null;
	}

	@Override
	public Packet<PacketListenerPlayOut> getNMS() {
		return new PacketPlayOutStopSound(PacketUtils.toMinecraftKey(sound.getKey()),
				net.minecraft.server.v1_16_R3.SoundCategory.valueOf(sound.name()));
	}

	@Override
	public int getPacketID() {
		return 0x5D;
	}

	@Override
	public String getProtocolURLString() {
		return "https://wiki.vg/Protocol#Stop_Sound";
	}
}
