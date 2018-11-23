/**
 * 
 */
package net.teamfps.gui.components;

import java.awt.Color;

import net.teamfps.gui.Input;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public abstract class GuiComponent {
	protected int x, y, w, h, sx, sy, id;
	protected Color color = new Color(184, 134, 11, 128);
	protected boolean visible = true;
	protected boolean dragging = false;
	protected int dx, dy;

	public abstract GuiComponent init(GuiScreen screen);

	public abstract void update(GuiScreen screen);

	public abstract void render(GuiScreen screen);

	public void draggable(int key) {
		if (isMouseOver()) {
			if (!dragging && Input.isButtonDown(key)) {
				dragging = true;
				dx = Input.DX - x;
				dy = Input.DY - y;
			}
		}
		if (dragging) {
			x = Input.DX - dx;
			y = Input.DY - dy;
			if (!Input.isButtonDown(key)) dragging = false;
		}
	}

	public boolean isMouseOver() {
		return (Input.MX >= x && Input.MX <= x + w && Input.MY >= y && Input.MY <= y + h);
	}

	public boolean isPressed(int key) {
		return isMouseOver() && Input.isButtonClicked(key);
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int w, int h) {
		this.w = w;
		this.h = h;
	}

	public void setSPos(int sx, int sy) {
		this.sx = sx;
		this.sy = sy;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GuiComponent set(int x, int y) {
		this.sx = this.x = x;
		this.sy = this.y = y;
		return this;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setW(int w) {
		this.w = w;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int getId() {
		return id;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the w
	 */
	public int getW() {
		return w;
	}

	/**
	 * @return the h
	 */
	public int getH() {
		return h;
	}

	public static float getWidth(GuiComponent[] list, float space, boolean row) {
		float width = 0;
		for (GuiComponent b : list) {
			if (row) {
				width += (b.getW() + space);
			} else {
				if (width < b.getW()) {
					width = b.getW();
				}
			}
		}
		return width;
	}

	public static float getHeight(GuiComponent[] list, float space, boolean row) {
		float height = 0;
		for (GuiComponent b : list) {
			if (row) {
				if (height < b.getH()) {
					height = b.getH();
				}
			} else {
				height += (b.getH() + space);
			}
		}
		return height;
	}

	public static float getWidth(GuiComponent[] list, float space) {
		float width = 0;
		for (GuiComponent b : list) {
			width += (b.getW() + space);
		}
		return width;
	}

	public static float getWidth(GuiComponent[] list) {
		float width = 0;
		for (GuiComponent b : list) {
			width += b.getW();
		}
		return width;
	}

	public static float getHeight(GuiComponent[] list, float space) {
		float height = 0;
		for (GuiComponent b : list) {
			height += (b.getH() + space);
		}
		return height;
	}

	public static float getHeight(GuiComponent[] list) {
		float height = 0;
		for (GuiComponent b : list) {
			height += b.getH();
		}
		return height;
	}

	/**
	 * @param b
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the visible
	 */
	public boolean isVisible() {
		return visible;
	}
}
