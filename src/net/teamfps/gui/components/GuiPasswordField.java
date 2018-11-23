/**
 * 
 */
package net.teamfps.gui.components;

import java.awt.Color;

/**
 * @author Zekye
 *
 */
public class GuiPasswordField extends GuiTextField {
	private boolean hide = true;

	/**
	 * @param name
	 * @param text
	 * @param fsize
	 * @param tcolor
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 */
	public GuiPasswordField(String name, String text, int fsize, Color tcolor, int x, int y, int w, int h) {
		super(name, text, fsize, tcolor, x, y, w, h);
	}

	@Override
	public GuiPasswordField init(GuiScreen screen) {
		super.init(screen);
		return this;
	}

	@Override
	public void update(GuiScreen screen) {
		super.update(screen);
		if (isPressed(2)) {
			hide = !hide;
		}
	}

	@Override
	public void render(GuiScreen screen) {
		if (!visible) return;
		screen.fillRect(x, y, w, h, color, false);
		screen.drawRect(x, y, w, h, 2, isMouseOver() ? focus ? Color.red : color : focus ? Color.green : Color.white, false);
		screen.drawString(name, fsize, x + nx, y + ny, color, false);
		screen.drawString(hide ? hide(text.toString()) : text.toString(), fsize, x + tx, y + ty, tcolor, false);
	}

	private String hide(String str) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			result.append("x");
		}
		return result.toString();
	}
}
