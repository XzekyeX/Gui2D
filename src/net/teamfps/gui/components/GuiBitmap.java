/**
 * 
 */
package net.teamfps.gui.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashMap;

import net.teamfps.gui.Input;
import net.teamfps.gui.Sprite;
import net.teamfps.gui.math.Vec2i;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public abstract class GuiBitmap implements GuiScreen {
	protected int width, height;
	protected int xOffs, yOffs;
	protected Graphics2D g;
	private boolean initialized = false;
	protected HashMap<Integer, Font> fonts = new HashMap<Integer, Font>();
	protected HashMap<Integer, FontMetrics> fontmetrics = new HashMap<Integer, FontMetrics>();

	public GuiBitmap(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public abstract void init();

	public Font getFont(int fsize) {
		if (fonts.containsKey(fsize)) return fonts.get(fsize);
		fonts.put(fsize, new Font("Tahoma", 1, fsize));
		System.out.println("new font has been added! fsize(" + fsize + ")");
		return fonts.get(fsize);
	}

	public FontMetrics getFontMetrics(int fsize) {
		if (g == null) {
			System.err.println("Exception: font metrics failed to create new one! hasn't yet been any graphics!");
			return null;
		}
		if (fontmetrics.containsKey(fsize)) return fontmetrics.get(fsize);
		fontmetrics.put(fsize, g.getFontMetrics(getFont(fsize)));
		System.out.println("new font metrics has been added! fsize(" + fsize + ")");
		return fontmetrics.get(fsize);
	}

	public int getWidth(String str, int fsize) {
		return getFontMetrics(fsize).stringWidth(str);
	}

	public Vec2i getSize(String str, int fsize) {
		FontMetrics f = getFontMetrics(fsize);
		if (f == null) return new Vec2i(0, 0);
		int width = f.stringWidth(str);
		int height = f.getHeight();
		return new Vec2i(width, height);
	}

	/**
	 * @param split
	 * @return
	 */
	public String getLongestStr(String[] split) {
		String result = "";
		if (split == null) return result;
		int length = 0;
		for (String s : split) {
			if (length < s.length()) {
				length = s.length();
				result = s;
			}
		}
		return result;
	}

	public void drawString(String str, int fsize, int x, int y, Color color, boolean offset) {
		if (offset) {
			x -= xOffs;
			y -= yOffs;
		}
		g.setColor(color);
		g.setFont(getFont(fsize));
		g.drawString(str, x, y + fsize);
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

	public void drawOval(int x, int y, int w, int h, Color color, boolean offset) {
		if (offset) {
			x -= xOffs;
			y -= yOffs;
		}
		g.setColor(color);
		g.drawOval(x, y, w, h);
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

	public void drawSprite(Sprite sprite, int x, int y) {
		if (sprite == null || (sprite != null && sprite.getImage() == null)) return;
		g.drawImage(sprite.getImage(), x, y, null);
	}

	public void drawSprite(Sprite sprite, int x, int y, boolean offset) {
		if (offset) {
			x -= xOffs;
			y -= yOffs;
		}
		drawSprite(sprite, x, y);
	}

	public void StrRect(String str, int fsize, Color text, int x, int y, int w, int h, Color in, Color out) {
		Rect(x, y, w, h, in, out, false);
		drawCenterStr(str, fsize, text, x, y, w, h);
	}

	public void Rect(int x, int y, int w, int h, Color in, Color out, boolean offset) {
		fillRect(x, y, w, h, in, offset);
		drawRect(x, y, w, h, 2, out, offset);
	}

	public void drawCenterStr(String str, int fsize, Color color, int x, int y, int w, int h) {
		Vec2i v = getSize(str, fsize);
		drawString(str, fsize, x + (w - v.getX()) / 2, y + (h - v.getY()) / 2, color, false);
	}

	public void drawLine(int x1, int y1, int x2, int y2, Color c) {
		g.setColor(c);
		g.drawLine(x1, y1, x2, y2);
	}

	public boolean isMouseOver(int x, int y, int w, int h, boolean offset) {
		return offset ? (Input.MX + getOffsetX() >= x && Input.MX + getOffsetX() <= x + w && Input.MY + getOffsetY() >= y && Input.MY + getOffsetY() <= y + h) : (Input.MX >= x && Input.MX <= x + w && Input.MY >= y && Input.MY <= y + h);
	}

	public void initGFX(Graphics2D g) {
		this.g = g;
		if (initialized == false) {
			initialized = true;
			init();
			System.out.println("Bitmap class has been initialized!");
		}
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public void setOffset(int x, int y) {
		this.xOffs = x;
		this.yOffs = y;
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
		// System.out.println("move(" + xa + "," + ya + ")");
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

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

}
