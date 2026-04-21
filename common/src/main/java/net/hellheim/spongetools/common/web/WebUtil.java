package net.hellheim.spongetools.common.web;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;

public class WebUtil {
	
	// TODO fix address check for self connection (used by Adventure to hash resource pack)
	public static boolean isValidConnection(MineConnection connection) {
		 /*
		final var address = connection.getClient().getInetAddress();
		final String host = address.getHostAddress();
		if (host.equals("0:0:0:0:0:0:0:1") || host.equals("127.0.0.1")) {
			// Adventure tries to hash pack locally
			return true;
		}
		
		return getPlayer(address) != null;
		*/
		return true;
	}
	
	public static @Nullable ServerPlayer getPlayer(InetAddress address) {
		byte[] mac = address.getAddress();
		return Sponge.server().streamOnlinePlayers()
				.filter(player ->
						Arrays.equals(player.connection().address().getAddress().getAddress(), mac))
				.findAny()
				.orElse(null);
	}
	
	public static byte[] getMAC(InetAddress address) {
		try {
			return NetworkInterface.getByInetAddress(address).getHardwareAddress();
		} catch (SocketException e) {
			e.printStackTrace();
			return null;
		}
	}
}
