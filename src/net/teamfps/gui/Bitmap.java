/**
 * 
 */
package net.teamfps.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import net.teamfps.gui.components.GuiScreen;
import net.teamfps.gui.math.Vec2i;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class Bitmap implements GuiScreen {
	protected int xOffs, yOffs, width, height;
	protected Graphics2D g;
	private boolean initialized = false;
	protected HashMap<Key, Font> fonts = new HashMap<Key, Font>();
	protected HashMap<Key, FontMetrics> fontmetrics = new HashMap<Key, FontMetrics>();
	private Action init;

	class Key {
		private Integer fontSize;
		private String fontName;

		public Key(String fontName, int fontSize) {
			this.fontName = fontName;
			this.fontSize = fontSize;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Key) {
				Key k = (Key) obj;
				return k != null ? fontName.equals(k.fontName) && fontSize == k.fontSize : false;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return fontName.hashCode() + fontSize.hashCode();
		}
	}

	public Bitmap() {

	}

	public Font loadFont(String font) {
		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream(font));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(f);
			return f;
		} catch (FontFormatException | IOException e) {
			// e.printStackTrace();
			System.err.println("Exception: " + e.getMessage());
		}
		return null;
	}

	public Font getFont(String name, int fsize) {
		Key key = new Key(name, fsize);
		if (fonts.containsKey(key)) return fonts.get(key);
		fonts.put(key, new Font(name, 1, fsize));
		System.out.println(name + " font has been added! fsize(" + fsize + ")");
		return fonts.get(key);
	}

	// public Font getFont(int fsize) {
	// if (fonts.containsKey(fsize)) return fonts.get(fsize);
	// fonts.put(fsize, new Font("Tahoma", 1, fsize));
	// System.out.println("new font has been added! fsize(" + fsize + ")");
	// return fonts.get(fsize);
	// }

	public FontMetrics getFontMetrics(String font, int fsize) {
		if (g == null) {
			System.err.println("Exception: font metrics failed to create new one! hasn't yet been any graphics!");
			return null;
		}
		Key key = new Key(font, fsize);
		if (fontmetrics.containsKey(key)) return fontmetrics.get(key);
		fontmetrics.put(key, g.getFontMetrics(getFont(font, fsize)));
		System.out.println(font + " font metrics has been added! fsize(" + fsize + ")");
		return fontmetrics.get(key);
	}

	public int getWidth(String str, String font, int fsize) {
		return getFontMetrics(font, fsize).stringWidth(str);
	}

	public Vec2i getSize(String str, String font, int fsize) {
		FontMetrics f = getFontMetrics(font, fsize);
		if (f == null) return new Vec2i(0, 0);
		int width = f.stringWidth(str);
		int height = f.getHeight();
		return new Vec2i(width, height);
	}

	public void drawString(String str, String font, int fsize, int x, int y, Color color, boolean offset) {
		if (offset) {
			x -= xOffs;
			y -= yOffs;
		}
		g.setColor(color);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(getFont(font, fsize));
		g.drawString(str, x, y + fsize);
	}

	public void drawString(String str, int fsize, int x, int y, Color color, boolean offset) {
		drawString(str, "Tahoma", fsize, x, y, color, offset);
	}

	public void drawString(String str, int fsize, double x, double y, Color color, boolean offset) {
		drawString(str, fsize, (int) x, (int) y, color, offset);
	}

	public void fillRect(int x, int y, int w, int h, Color color, boolean offset) {
		if (offset) {
			x -= xOffs;
			y -= yOffs;
		}
		g.setColor(color);
		g.fillRect((int) x, (int) y, (int) w, (int) h);
	}

	public void drawRect(int x, int y, int w, int h, int b, Color color, boolean offset) {
		fillRect(x, y, w, b, color, offset);
		fillRect(x, y, b, h, color, offset);
		fillRect(x, y + h - b, w, b, color, offset);
		fillRect(x + w - b, y, b, h, color, offset);
	}

	public void drawSprite(Sprite sprite, int x, int y, int w, int h) {
		if (sprite == null || (sprite != null && sprite.getImage() == null)) return;
		g.drawImage(sprite.getImage(), x, y, w, h, null);
	}

	public void drawSprite(Sprite sprite, int x, int y, int w, int h, boolean offset) {
		if (offset) {
			x -= xOffs;
			y -= yOffs;
		}
		drawSprite(sprite, x, y, w, h);
	}

	public void drawSprite(Sprite sprite, double x, double y) {
		if (sprite == null || (sprite != null && sprite.getImage() == null)) return;
		g.drawImage(sprite.getImage(), (int) x, (int) y, null);
	}

	public void drawSprite(Sprite sprite, double x, double y, boolean offset) {
		if (offset) {
			x -= xOffs;
			y -= yOffs;
		}
		drawSprite(sprite, x, y);
	}

	public void StrRect(String str, String font, int fsize, Color text, int x, int y, int w, int h, Color in, Color out) {
		Rect(x, y, w, h, in, out, false);
		drawCenterStr(str, font, fsize, text, x, y, w, h);
	}

	public void StrRect(String str, int fsize, Color text, int x, int y, int w, int h, Color in, Color out) {
		StrRect(str, "Tahoma", fsize, text, x, y, w, h, in, out);
	}

	public void Rect(int x, int y, int w, int h, Color in, Color out, boolean offset) {
		fillRect(x, y, w, h, in, offset);
		drawRect(x, y, w, h, 2, out, offset);
	}

	public void drawCenterStr(String str, int fsize, Color color, int x, int y, int w, int h) {
		drawCenterStr(str, "Tahoma", fsize, color, x, y, w, h);
	}

	public void drawCenterStr(String str, String font, int fsize, Color color, int x, int y, int w, int h) {
		Vec2i v = getSize(str, font, fsize);
		drawString(str, font, fsize, x + (w - v.getX()) / 2, y + (h - v.getY()) / 2, color, false);
	}

	public void drawLine(int x1, int y1, int x2, int y2, Color c) {
		g.setColor(c);
		g.drawLine(x1, y1, x2, y2);
	}

	public void drawOval(int x, int y, int w, int h, Color color, boolean offset) {
		if (offset) {
			x -= xOffs;
			y -= yOffs;
		}
		g.setColor(color);
		g.drawOval(x, y, w, h);
	}

	public boolean isMouseOver(int x, int y, int w, int h, boolean offset) {
		return offset ? (Input.MX + getOffsetX() >= x && Input.MX + getOffsetX() <= x + w && Input.MY + getOffsetY() >= y && Input.MY + getOffsetY() <= y + h) : (Input.MX >= x && Input.MX <= x + w && Input.MY >= y && Input.MY <= y + h);
	}

	public void initGFX(Graphics2D g) {
		this.g = g;
		if (initialized == false) {
			initialized = true;
			if (init != null) init.action();
			System.out.println("Bitmap class has been initialized!");
		}
	}

	public void setInit(Action init) {
		this.init = init;
	}

	public void setOffset(int x, int y) {
		this.xOffs = x;
		this.yOffs = y;
	}

	public void setCenter(float x, float y, float w, float h) {
		this.xOffs = (int) (x) - width / 2;
		this.yOffs = (int) (y) - height / 2;
	}

	public void movement(double speed) {
		double xa = 0;
		double ya = 0;
		if (Input.isKeyDown(KeyEvent.VK_A)) {
			xa -= speed;
		}
		if (Input.isKeyDown(KeyEvent.VK_D)) {
			xa += speed;
		}
		if (Input.isKeyDown(KeyEvent.VK_W)) {
			ya -= speed;
		}
		if (Input.isKeyDown(KeyEvent.VK_S)) {
			ya += speed;
		}
		move(xa, ya);
	}

	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		this.xOffs += xa;
		this.yOffs += ya;
	}

	/**
	 * @return the xOffs
	 */
	public int getOffsetX() {
		return xOffs;
	}

	/**
	 * @return the yOffs
	 */
	public int getOffsetY() {
		return yOffs;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setBounds(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public boolean isInBounds(int x, int y, int w, int h) {
		// return (getOffsetX() > x && getOffsetX() + getWidth() < x + w && getOffsetY() > y && getOffsetY() + getHeight() < y + h);
		return xOffs < x && xOffs + width > x + w && yOffs < y && yOffs + height > y + h;
		// return false;
	}

}
