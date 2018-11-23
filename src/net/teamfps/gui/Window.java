package net.teamfps.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JFrame;

/**
 * 
 * @author Zekye
 *
 */
public abstract class Window extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean running = false;
	private Input input;
	private JFrame f;
	private int fps, ups;
	protected int width, height;
	protected final String VERSION = "V.1.0";
	protected final String ENGINE = "Gui2D";
	protected final String ENGINE_TITLE = "[" + ENGINE + " " + VERSION + "]";
	protected String title = "";
	protected boolean resizable = true;
	protected boolean alwaysOnTop = false;
	protected boolean undecorated = false;
	protected Location location;
	protected float opacity = 1.0f;

	protected JFrame.Type type = null;

	public Window(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
	}

	public Window(String title, int width, int height, boolean resizable, boolean alwaysOnTop, boolean undecorated, float opacity, Location location) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.resizable = resizable;
		this.alwaysOnTop = alwaysOnTop;
		this.undecorated = undecorated;
		this.opacity = opacity;
		this.location = location;
	}

	public void start() {
		if (running) return;
		running = true;
		thread = new Thread(this, "Window Thread!");
		thread.start();
	}

	public void stop() {
		if (!running) return;
		running = false;
		thread.interrupt();
	}

	private boolean initialize() {
		if (createWindow()) {
			input = new Input();
			addKeyListener(input);
			addMouseListener(input);
			addMouseWheelListener(input);
			addMouseMotionListener(input);
			init();
			return true;
		}
		return false;
	}

	protected void setType(JFrame.Type type) {
		this.type = type;
	}

	private boolean createWindow() {
		String title = this.title + " " + ENGINE_TITLE;
		f = new JFrame();
		f.add(this, "Center");
		f.setSize(width, height);
		f.setTitle(title);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (type != null) f.setType(type);
		f.setLocationRelativeTo(null);
		if (location != null) f.setLocation(Location.getPoint(location, width, height));
		f.setUndecorated(undecorated);
		f.setOpacity(opacity);
		f.setAlwaysOnTop(alwaysOnTop);
		f.setResizable(resizable);
		f.setVisible(true);
		return true;
	}

	public void run() {
		long timer = System.currentTimeMillis();
		long lastTime = System.nanoTime();
		double delta = 0;
		double ns = 1000000000 / 60.0;
		int ups = 0;
		int fps = 0;
		if (!initialize()) return;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				delta--;
				tick();
				ups++;
			}
			render();
			fps++;
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				setFpsAndUps(fps, ups);
				fps = 0;
				ups = 0;
			}
		}
	}

	private void tick() {
		if (input != null) input.update();
		if (isResized()) {
			width = getWidth();
			height = getHeight();
			System.out.println(ENGINE_TITLE + ": Resize(" + width + " x " + height + ")");
			resized();
		}
		update();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		render(g);
		g.dispose();
		bs.show();
	}

//	public int[] getImage() {
//		getToolkit().
//		int[] pixels = new int[width * height];
////		for (int x = 0; x < width; x++) {
////			for (int y = 0; y < height; y++) {
////				pixels[x + y * width] = pixel;
////			}
////		}
//		return pixels;
//	}

	private void setFpsAndUps(int fps, int ups) {
		this.fps = fps;
		this.ups = ups;
	}

	public String getFpsAndUps() {
		return "fps[" + fps + "], ups[" + ups + "]";
	}

	public boolean isResized() {
		return (width != getWidth() || height != getHeight());
	}

	public abstract void init();

	public abstract void resized();

	public abstract void update();

	public abstract void render(Graphics g);

}
