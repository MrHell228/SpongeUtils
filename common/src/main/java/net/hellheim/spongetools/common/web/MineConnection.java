package net.hellheim.spongetools.common.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MineConnection implements Runnable {
	
	protected final MineHttpd server;
	protected final Socket client;

	public MineConnection(MineHttpd server, Socket client) {
		this.server = server;
		this.client = client;
	}

	public Socket getClient() {
		return this.client;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(this.client.getInputStream(), "8859_1"));
			OutputStream out = this.client.getOutputStream();
			PrintWriter pout = new PrintWriter(new OutputStreamWriter(out, "8859_1"), true);
			String request = in.readLine();
			this.server.onClientRequest(this, request);

			Matcher get = Pattern.compile("GET /?(\\S*).*").matcher(request);
			if (get.matches()) {
				request = get.group(1);
				File result = this.server.requestFileCallback(this, request);
				if (result == null) {
					pout.println("HTTP/1.0 400 Bad Request");
					this.server.onRequestError(this, 400);
				} else {
					try {
						// Writes zip files specifically; Designed for resource pack hosting
						out.write("HTTP/1.0 200 OK\r\n".getBytes());
						out.write("Content-Type: application/zip\r\n".getBytes());
						out.write(("Content-Length: " + result.length() + "\r\n").getBytes());
						out.write(("Date: " + new Date().toGMTString() + "\r\n").getBytes());
						out.write("Server: MineHttpd\r\n\r\n".getBytes());
						FileInputStream fis = new FileInputStream(result);
						byte[] data = new byte[64 * 1024];
						for (int read; (read = fis.read(data)) > -1;) {
							out.write(data, 0, read);
						}
						out.flush();
						fis.close();
						this.server.onSuccessfulRequest(this, request);
					} catch (FileNotFoundException e) {
						pout.println("HTTP/1.0 404 Object Not Found");
						this.server.onRequestError(this, 404);
					}
				}
			} else {
				pout.println("HTTP/1.0 400 Bad Request");
				this.server.onRequestError(this, 400);
			}
			this.client.close();
		} catch (IOException e) {
			this.server.logger.error("I/O error ", e);
		}
	}
}
