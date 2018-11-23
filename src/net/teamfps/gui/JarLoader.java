package net.teamfps.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

/**
 * 
 * @author Zekye
 *
 */
public class JarLoader implements Runnable {
	protected Thread thread;
	protected String jar, args;
	protected boolean running = false;

	public JarLoader(String folder, String jar, String args) {
		File f = extractJar(folder, jar);
		if (f != null) {
			this.jar = f.getAbsolutePath();
			this.args = args;
			start();
		} else {
			System.err.println("File not found!");
		}
	}

	public File extractJar(String fol, String jar) {
		InputStream file = this.getClass().getResourceAsStream("/" + jar);
		if (file != null) {
			try {
				File folder = new File(fol);
				if (!folder.exists()) {
					folder.mkdirs();
					System.out.println("Folders has been written out!");
				}
				File f = new File(folder, jar);
				if (!f.exists()) {
					Files.copy(file, f.getAbsoluteFile().toPath());
					System.out.println("File has been written out!");
				}
				return f;
			} catch (IOException e) {
				System.err.println("IOException: " + e.getMessage());
			}
		}
		return null;
	}

	public void run() {
		try {
			Process proc = Runtime.getRuntime().exec("java -jar " + jar + " " + args);
			System.out.println("proc: " + proc);
			InputStream err = proc.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(err));
			String str = "";
			while ((str = br.readLine()) != null) {
				System.err.println(str);
			}
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
		}
	}

	public void start() {
		if (running) return;
		thread = new Thread(this, "Jar Loader Thread!");
		thread.start();
		running = true;
	}

	public void stop() {
		if (!running) return;
		thread.interrupt();
		running = false;
	}
}
