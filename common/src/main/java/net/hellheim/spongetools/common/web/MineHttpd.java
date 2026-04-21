package net.hellheim.spongetools.common.web;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;

import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.api.Server;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;

/*
 * Original logic was copied from
 * https://github.com/Aeternum-Studios/LocalResourcePackHoster
 */
public class MineHttpd extends Thread {
	
	private volatile boolean running = true;

	protected final int port;
	protected final ServerSocket socket;
	protected final Logger logger;
	protected final boolean verbose = true;
	protected final File pack;
	private final EventListener eventListener;
	
	public MineHttpd(Logger logger, File pack, int port) throws IOException {
		this.port = port;
		this.socket = new ServerSocket(port);
		this.socket.setReuseAddress(true);
		this.logger = logger;
		this.pack = pack;
		this.eventListener = new EventListener();
	}
	
	public static Optional<MineHttpd> tryCreate(final Logger logger, final File pack, final int port) {
		try {
			return Optional.of(new MineHttpd(logger, pack, port));
		} catch (final IOException e) {
			logger.error("Unable to start the http daemon", e);
			return Optional.empty();
		}
	}
	
	public EventListener eventListener() {
		return this.eventListener;
	}
	
	@Override
	public void run() {
		while (this.running) {
			try {
				new Thread(new MineConnection(this, this.socket.accept())).start();
			} catch (final IOException e) {
				this.logger.warn("A thread was interrupted in the http daemon!");
			}
		}
		
		if (!this.socket.isClosed()) {
			try {
				this.socket.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void terminate() {
		this.running = false;
		if (!this.socket.isClosed()) {
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Executes whenever a client requests something
	 * 
	 * @param connection The connection that is requesting a file
	 * @param request    The path to the file requested
	 * @return A file to return to them; null if the file they requested is invalid
	 */
	public @Nullable File requestFileCallback(MineConnection connection, String request) {
		this.logger.info("Request: " + request);
		
		if (!WebUtil.isValidConnection(connection)) {
			verbose("Unknown connection from '" + connection.getClient().getInetAddress() + "'. Aborting...");
			return null;
		}
		
		// TODO
		if (!request.equals("resourcepack_id")) {
			verbose("Unknown pack id '" + request + "'");
			return null;
		}
		
		return this.pack;

		/*if (!resourcepacks.containsKey(request)) {
			return null;
		}

		if (player.hasPermission("localresourcepack.pack." + request)) {
			verbose("Serving '" + request + "' to " + player.getName() + "(" + connection.getClient().getInetAddress()
					+ ")");
			return resourcepacks.get(request);
		} else {
			verbose("Denied access to '" + request + "' from " + player.name() + " due to insufficient permissions");
		}
		return null;*/
	}

	/**
	 * Occurs once the file has been sent
	 * 
	 * @param connection The connection that successfully got sent a file
	 * @param request    The request of the client
	 */
	public void onSuccessfulRequest(MineConnection connection, String request) {
		verbose("Successfully served '" + request + "' to " + connection.getClient().getInetAddress());
	}

	/**
	 * Called before the requestFileCallback; Contains the original request
	 * 
	 * @param connection The connection that has sent a request
	 * @param request    The raw request of the client
	 */
	public void onClientRequest(MineConnection connection, String request) {
		verbose("Request '" + request + "' recieved from " + connection.getClient().getInetAddress());
	}

	/**
	 * Handle http error response codes here
	 * 
	 * @param connection The connection that is getting an error on request
	 * @param code       The http response code
	 */
	public void onRequestError(MineConnection connection, int code) {
		verbose("Error " + code + " when attempting to serve " + connection.getClient().getInetAddress());
	}

	private void verbose(Object object) {
		if (this.verbose) {
			this.logger.info(object.toString());
		}
	}
	
	public int port() {
		return this.port;
	}
	
	public class EventListener {
		
		@Listener
		public void onServerStart(final StartedEngineEvent<Server> event) {
			MineHttpd.this.start();
			MineHttpd.this.logger.info("Successfully started the http daemon!");
		}
		
		@Listener
		public void onServerStop(final StoppingEngineEvent<Server> event) {
			MineHttpd.this.terminate();
			MineHttpd.this.logger.info("Successfully stopped the http daemon!");
		}
	}
}
