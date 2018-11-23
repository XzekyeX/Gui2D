/**
 * 
 */
package net.teamfps.gui.components;

import java.awt.Color;

import net.teamfps.gui.math.Vec2i;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class GuiText extends GuiComponent {
	protected int tx, ty;
	protected String text;
	protected int fsize;
	protected Color tcolor;

	public GuiText(String text, int fsize, Color tcolor, int x, int y, int w, int h) {
		this.text = text;
		this.fsize = fsize;
		this.tcolor = tcolor;
		this.sx = this.x = x;
		this.sy = this.y = y;
		this.w = w;
		this.h = h;
	}

	@Override
	public GuiText init(GuiScreen screen) {
		Vec2i size = screen.getSize(text,"Tahoma", fsize);
		this.tx = (w - size.getX()) / 2;
		this.ty = (h - size.getY()) / 2;
		return this;
	}

	@Override
	public void update(GuiScreen screen) {

	}

	@Override
	public void render(GuiScreen screen) {
		if (!visible) return;
		screen.fillRect(x, y, w, h, color, false);
		screen.drawRect(x, y, w, h, 2, Color.white, false);
		if (text.contains("\n")) {
			String[] split = text.split("\n");
			for (int i = 0; i < split.length; i++) {
				screen.drawString(split[i].trim(), fsize, x + 4, y + 4 + i * fsize, tcolor, false);
			}
		} else {
			screen.drawString(text, fsize, x + tx, y + ty, tcolor, false);
		}
	}

	public GuiText setText(String text) {
		this.text = text;
		return this;
	}

}
