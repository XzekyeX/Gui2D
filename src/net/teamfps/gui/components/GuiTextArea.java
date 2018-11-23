package net.teamfps.gui.components;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.teamfps.gui.Input;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class GuiTextArea extends GuiComponent {
	protected List<String> texts = new ArrayList<String>();
	private int fsize;
	private boolean focus = false;

	public GuiTextArea(int fsize, int x, int y, int w, int h) {
		this.fsize = fsize;
		this.sx = this.x = x;
		this.sy = this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public GuiTextArea init(GuiScreen screen) {

		return this;
	}

	@Override
	public void update(GuiScreen screen) {
		if (!visible) return;
		if (isPressed(1)) {
			focus = true;
			System.out.println("Focus!");
		}
		if (!isMouseOver()) {
			if (Input.isButtonClicked(1)) {
				focus = false;
			}
		}
		if (focus) {

		}

	}

	@Override
	public void render(GuiScreen screen) {
		if (!visible) return;
		screen.fillRect(x, y, w, h, color, false);
		screen.drawRect(x, y, w, h, 2, isMouseOver() ? focus ? Color.red : color : focus ? Color.green : Color.white, false);
		int k = 0;
		for (int i = 0; i < texts.size(); i++) {
			k += i * fsize;
			String s = texts.get(i);
			List<String> l = trim(screen, s, fsize, w);
			if (l.size() > 0) for (int j = 0; j < l.size(); j++) {
				k += j * fsize;
				screen.drawString(l.get(j), fsize, x + 8, y + k, Color.white, false);
			}
			else {
				screen.drawString(s, fsize, x + 8, y + k, Color.white, false);
			}
		}
	}

	private List<String> trim(GuiScreen screen, String str, int fsize, int w) {
		List<String> result = new ArrayList<String>();
		int width = screen.getWidth(str, "Tahoma", fsize);
		if (width > w) {
			int beginIndex = width - w;
			if (beginIndex > 0 && beginIndex < str.length()) {
				String s = str.substring(0, beginIndex);
				String e = str.substring(beginIndex);
				result.add(s);
				result.add(e);
				// result.addAll(trim(screen, e, fsize, w));
			}
		}
		return result;
	}

	public void append(String text) {
		texts.add(text);
	}
}
