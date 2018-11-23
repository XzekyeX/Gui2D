/**
 * 
 */
package net.teamfps.gui.components;

import java.awt.Color;

import net.teamfps.gui.math.MathHelper;

/**
 * @author Zekye
 *
 */
public class GuiSlider extends GuiComponent {
	protected Slider slider;
	private float value = 0;
	private float max = 100.0f;
	private float min = 0.0f;

	private int off = 6;

	private boolean horizontal = false;

	public GuiSlider(int x, int y, int w, int h, float min, float max, boolean horizontal) {
		this.horizontal = horizontal;
		this.sx = this.x = x;
		this.sy = this.y = y;
		this.min = min;
		this.max = max;
		this.w = horizontal ? h : w;
		this.h = horizontal ? w : h;
		this.slider = new Slider(horizontal ? 12 : this.w - off, horizontal ? this.h - off : 12);
	}

	@Override
	public GuiSlider init(GuiScreen screen) {
		return this;
	}

	@Override
	public void update(GuiScreen screen) {
		slider.update(screen);
		if (horizontal) {
			slider.x = MathHelper.clamp(slider.x, x + (off / 2), x + w - (slider.w + (off / 2)));
			slider.y = y + (off / 2);
			value = scale(((slider.x - (off / 2)) - (x)), 0, (w - slider.w - off), min, max);
		} else {
			slider.x = x + (off / 2);
			slider.y = MathHelper.clamp(slider.y, y + (off / 2), y + h - (slider.h + (off / 2)));
			value = scale(((slider.y - (off / 2)) - (y)), 0, (h - slider.h - off), min, max);
		}
	}

	// -------(b-a)(x - min)
	// f(x) = -------------- + a
	// ---------max - min
	private float scale(float valueIn, float baseMin, float baseMax, float limitMin, float limitMax) {
		return ((limitMax - limitMin) * (valueIn - baseMin) / (baseMax - baseMin)) + limitMin;
	}

	@Override
	public void render(GuiScreen screen) {
		if (!visible) return;
		screen.fillRect(x, y, w, h, color, false);
		screen.drawRect(x, y, w, h, 2, Color.white, false);
		screen.drawString("" + String.format("%.2f", value), 12, x, y - 16, Color.white, false);
		slider.render(screen);
	}

	public float getValue() {
		return value;
	}

	protected class Slider extends GuiComponent {
		public Slider(int w, int h) {
			this.w = w;
			this.h = h;
		}

		@Override
		public GuiComponent init(GuiScreen screen) {
			return null;
		}

		@Override
		public void update(GuiScreen screen) {
			draggable(1);
		}

		@Override
		public void render(GuiScreen screen) {
			screen.fillRect(x, y, w, h, color, false);
			screen.drawRect(x, y, w, h, 2, Color.white, false);
		}

	}

}
