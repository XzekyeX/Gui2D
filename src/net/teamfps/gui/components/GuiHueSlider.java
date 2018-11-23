package net.teamfps.gui.components;

import java.awt.Color;
/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class GuiHueSlider extends GuiSlider {
	private Color value = Color.white;

	public GuiHueSlider(int x, int y, int w, int h, float min, float max, boolean horizontal) {
		super(x, y, w, h, min, max, horizontal);
	}

	@Override
	public void render(GuiScreen screen) {
		// super.render(screen);
		for (int i = 0; i < h; i++) {
			float hue = i / (float) h;
			Color shade = new Color(Color.HSBtoRGB(hue, 1, 1));
			screen.drawLine(x, y + i, x + w - 2, y + i, shade);
			if (i == (int) getValue()) {
				value = shade;
			}
		}
		screen.drawRect(x, y, w, h, 2, Color.white, false);
		slider.render(screen);
	}

	public Color getColorValue() {
		return value;
	}
}
