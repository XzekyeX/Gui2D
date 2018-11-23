package net.teamfps.gui.components;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import net.teamfps.gui.Input;
import net.teamfps.gui.Sprite;
import net.teamfps.gui.math.MathHelper;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class GuiColorPicker extends GuiComponent {

	private ColorPicker picker;
	private GuiHueSlider hue;
	private GuiSlider weight;
	private GuiText text;

	public GuiColorPicker(Sprite mask, int x, int y) {
		this.x = x;
		this.y = y;
		this.picker = new ColorPicker(mask);
		this.w = picker.w + 160;
		this.h = picker.h + 32;
		setColor(0xffff0000, 0.2D);
		this.hue = new GuiHueSlider(x, y, 20, picker.h, 0, picker.h, false);
		this.weight = new GuiSlider(x, y, 20, picker.h, 0, 1.0f, false);
		this.text = new GuiText("R=\nG=\nB=", 16, Color.white, x, y, 64, 64);
	}

	@Override
	public GuiComponent init(GuiScreen screen) {
		this.text.init(screen);
		return null;
	}

	public void setColor(int color, double weight) {
		this.picker.setColor(color, weight);
	}

	@Override
	public void update(GuiScreen screen) {
		this.picker.x = x + 16;
		this.picker.y = y + 16;
		this.picker.update(screen);
		this.hue.x = this.picker.x + this.picker.w + 8;
		this.hue.y = this.picker.y;
		this.hue.update(screen);
		this.weight.x = this.hue.x + this.hue.w + 8;
		this.weight.y = this.picker.y;
		this.weight.update(screen);
		this.text.x = this.weight.x + this.weight.w + 8;
		this.text.y = this.picker.y;
		this.text.setText("R=" + getValue().getRed() + "\nG=" + getValue().getGreen() + "\nB=" + getValue().getBlue());
		this.text.setColor(getValue());
		this.setColor(this.hue.getColorValue().getRGB(), this.weight.getValue());
		// this.setColor(this.getValue());
		draggable(2);
	}

	public void render(GuiScreen screen) {
		screen.fillRect(x, y, w, h, color, false);
		screen.drawRect(x, y, w, h, 2, Color.white, false);
		this.picker.render(screen);
		this.hue.render(screen);
		this.weight.render(screen);
		this.text.render(screen);
	}

	public Color getValue() {
		return this.picker.getValue();
	}

	private class ColorPicker extends GuiComponent {
		private Sprite image, mask;
		private int px, py, pick, oldColor;
		private int[] pixels, colour;
		private double oldWeight;
		private Color value;

		public ColorPicker(Sprite mask) {
			this.mask = mask;
			this.w = mask.getWidth();
			this.h = mask.getHeight();
			this.pixels = new int[w * h];
			this.colour = new int[w * h];
			this.image = new Sprite(0xff000000, w, h, BufferedImage.TYPE_INT_ARGB);
		}

		@Override
		public GuiComponent init(GuiScreen screen) {
			return null;
		}

		public void setColor(int color, double weight) {
			if (oldColor != color || oldWeight != weight) {
				Arrays.fill(colour, color);
				pixels = Sprite.Blend(colour, mask.getPixels(), weight);
				this.image.getImage().setRGB(0, 0, w, h, pixels, 0, w);
				oldColor = color;
				oldWeight = weight;
			}
		}

		@Override
		public void update(GuiScreen screen) {
			if (isMouseOver() && Input.isButtonDown(1)) {
				px = MathHelper.clamp(Input.DX - x, 0, w - 1);
				py = MathHelper.clamp(Input.DY - y, 0, h - 1);
			}
			if (pixels != null) {
				int picked = pixels[px + py * w];
				if (picked != pick) {
					this.value = new Color(pick);
					pick = picked;
				}
			}
		}

		@Override
		public void render(GuiScreen screen) {
			screen.fillRect(x, y, w, h, Color.black, false);
			screen.drawSprite(image, x, y, w, h);
			screen.drawRect(x, y, w, h, 2, Color.white, false);
			screen.drawRect(x + px - 4, y + py - 4, 8, 8, 2, Color.white, false);
		}

		public Color getValue() {
			return value;
		}

	}

}
