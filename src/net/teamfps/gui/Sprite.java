/**
 * 
 */
package net.teamfps.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import net.teamfps.gui.math.MathHelper;

/**
 * @author Zekye
 *
 */
public class Sprite {
	protected BufferedImage image;
	protected String path;
	protected int width, height;
	protected int imageType;
	protected int[] pixels;

	/**
	 * 
	 * @param path
	 */
	public Sprite(String path) {
		this.path = path;
		load();
	}

	public Sprite(Color color, int width, int height, int imageType) {
		this(color != null ? color.getRGB() : 0xff000000, width, height, imageType);
	}

	public Sprite(BufferedImage img) {
		this.image = img;
		this.imageType = img.getType();
		this.width = img.getWidth();
		this.height = img.getHeight();
		this.pixels = new int[width * height];
		this.image.getRGB(0, 0, width, height, pixels, 0, width);
	}

	/**
	 * 
	 * @param color
	 * @param width
	 * @param height
	 * @param imageType
	 */
	public Sprite(int color, int width, int height, int imageType) {
		this.image = new BufferedImage(width, height, imageType);
		this.imageType = imageType;
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		Arrays.fill(this.pixels, color);
		this.image.setRGB(0, 0, width, height, this.pixels, 0, width);
	}

	/**
	 * 
	 * @param pixels
	 * @param width
	 * @param height
	 * @param imageType
	 */
	public Sprite(int[] pixels, int width, int height, int imageType) {
		this.image = new BufferedImage(width, height, imageType);
		this.imageType = imageType;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
		this.image.setRGB(0, 0, width, height, pixels, 0, width);
	}

	/**
	 * 
	 * @param sprite
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param imageType
	 * @return
	 */
	public static Sprite Split(Sprite s, int x, int y, int width, int height, int imageType) {
		int[] pixels = new int[width * height];
		for (int h = 0; h < height; h++) {
			for (int w = 0; w < width; w++) {
				pixels[w + h * width] = s.pixels[((w + x) + (h + y) * s.getWidth())];
			}
		}
		return new Sprite(pixels, width, height, imageType);
	}

	/**
	 * 
	 * @param sprite
	 * @param w
	 * @param h
	 * @param imageType
	 * @return
	 */
	public static Sprite[] Split(Sprite s, int w, int h, int imageType) {
		int sw = s.width / w;
		int sh = s.height / h;
		Sprite[] sprites = new Sprite[sw * sh];
		for (int y = 0; y < sh; y++) {
			for (int x = 0; x < sw; x++) {
				sprites[x + y * sw] = Split(s, x * w, y * h, w, h, imageType);
			}
		}
		return sprites;
	}

	public static Sprite[][] Split2D(Sprite s, int w, int h, int imageType) {
		int sw = s.width / w;
		int sh = s.height / h;
		Sprite[][] sprites = new Sprite[sh][sw];
		for (int y = 0; y < sh; y++) {
			for (int x = 0; x < sw; x++) {
				sprites[y][x] = Split(s, x * w, y * h, w, h, imageType);
			}
		}
		return sprites;
	}

	public static Sprite Blend(Sprite s, Sprite t, double weight) {
		int w = s.getWidth();
		int h = s.getHeight();
		if (s.getPixels().length != t.getPixels().length) return s;
		int[] pixels = new int[w * h];
		for (int i = 0; i < pixels.length; i++) {
			Color c1 = new Color(s.getPixels()[i]);
			Color c2 = new Color(t.getPixels()[i]);
			int r = (int) (c1.getRed() * weight + c2.getRed() * (1.0 - weight));
			int g = (int) (c1.getGreen() * weight + c2.getGreen() * (1.0 - weight));
			int b = (int) (c1.getBlue() * weight + c2.getBlue() * (1.0 - weight));
			int a = (int) (c1.getAlpha() * weight + c2.getAlpha() * (1.0 - weight));
			pixels[i] = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
		}
		return new Sprite(pixels, w, h, BufferedImage.TYPE_INT_ARGB);
	}

	public static int[] Blend(int[] first, int[] second, double weight) {
		int[] result = new int[first.length];
		for (int i = 0; i < result.length; i++) {
			int r = (int) (MathHelper.getRed(first[i]) * weight + MathHelper.getRed(second[i]) * (1.0 - weight));
			int g = (int) (MathHelper.getGreen(first[i]) * weight + MathHelper.getGreen(second[i]) * (1.0 - weight));
			int b = (int) (MathHelper.getBlue(first[i]) * weight + MathHelper.getBlue(second[i]) * (1.0 - weight));
			int a = (int) (MathHelper.getAlpha(first[i]) * weight + MathHelper.getAlpha(second[i]) * (1.0 - weight));
			result[i] = MathHelper.toColor(r, g, b, a);
		}
		return result;
	}

	public static Sprite Filter(Sprite s, Filter filter) {
		System.out.println("Using Filter: " + filter);
		return Filter(s, filter.getFilter(), filter.getW(), filter.getH(), filter.getFactor(), filter.getBias());
	}

	public static Sprite Filter(Sprite s, double[] filter, int fw, int fh, double factor, double bias) {
		long lastTime = System.currentTimeMillis();
		int w = s.getWidth();
		int h = s.getHeight();
		int[] result = new int[w * h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				double red = 0.0, green = 0.0, blue = 0.0;
				// multiply every value of the filter with corresponding image pixel
				for (int fy = 0; fy < fh; fy++) {
					for (int fx = 0; fx < fw; fx++) {
						int imageX = (x - fw / 2 + fx + w) % w;
						int imageY = (y - fh / 2 + fy + h) % h;
						Color c = new Color(s.getPixels()[imageY * w + imageX]);
						red += c.getRed() * filter[fx + fy * fw];
						green += c.getGreen() * filter[fx + fy * fw];
						blue += c.getBlue() * filter[fx + fy * fw];
					}
				}
				int r = MathHelper.clamp((int) (factor * red + bias), 0, 255);
				int g = MathHelper.clamp((int) (factor * green + bias), 0, 255);
				int b = MathHelper.clamp((int) (factor * blue + bias), 0, 255);
				Color c = new Color(r, g, b);
				result[x + y * w] = c.getRGB();
			}
		}
		long now = System.currentTimeMillis();
		long time = now - lastTime;
		System.out.println("Filtered in " + time + "ms");
		return new Sprite(result, w, h, s.getImageType());
	}

	public static Sprite FilterMedian(Sprite s, Filter filter) {
		System.out.println("Using Median Filter: " + filter);
		return FilterMedian(s, filter.getFilter(), filter.getW(), filter.getH(), filter.getFactor(), filter.getBias());
	}

	public static Sprite FilterMedian(Sprite s, double[] filter, int fw, int fh, double factor, double bias) {
		long lastTime = System.currentTimeMillis();
		int w = s.getWidth();
		int h = s.getHeight();
		int[] result = new int[w * h];
		int[] red = new int[fw * fh];
		int[] green = new int[fw * fh];
		int[] blue = new int[fw * fh];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int n = 0;
				// multiply every value of the filter with corresponding image pixel
				for (int fy = 0; fy < fh; fy++) {
					for (int fx = 0; fx < fw; fx++) {
						int imageX = (x - fw / 2 + fx + w) % w;
						int imageY = (y - fh / 2 + fy + h) % h;
						Color c = new Color(s.getPixels()[imageY * w + imageX]);
						red[n] = c.getRed();
						green[n] = c.getGreen();
						blue[n] = c.getBlue();
						n++;
					}
				}
				int fs = fw * fh;
				int r = red[selectKth(red, 0, fs, fs / 2)];
				int g = green[selectKth(green, 0, fs, fs / 2)];
				int b = blue[selectKth(blue, 0, fs, fs / 2)];
				Color c = new Color(r, g, b);
				result[x + y * w] = c.getRGB();
			}
		}
		long now = System.currentTimeMillis();
		long time = now - lastTime;
		System.out.println("Filtered in " + time + "ms");
		return new Sprite(result, w, h, s.getImageType());
	}

	private static int selectKth(int[] data, int s, int e, int k) {
		// 5 or less elements: do a small insertion sort
		if (e - s <= 5) {
			for (int i = s + 1; i < e; i++) {
				for (int j = i; j > 0 && data[j - 1] > data[j]; j--) {
					swap(j, j - 1, data);
				}
			}
			return s + k;
		}

		int p = (s + e) / 2; // choose simply center element as pivot

		// partition around pivot into smaller and larger elements
		swap(p, e - 1, data); // temporarily move pivot to the end

		int j = s; // new pivot location to be calculated
		for (int i = s; i + 1 < e; i++) {
			if (data[i] < data[e - 1]) {
				swap(i, j++, data);
			}
		}

		swap(j, e - 1, data);

		// recurse into the applicable partition
		if (k == j - s) return s + k;
		else if (k < j - s) return selectKth(data, s, j, k);
		else return selectKth(data, j + 1, e, k - j + s - 1); // subtract amount of smaller elements from k
	}

	private static void swap(int i, int j, int[] data) {
		int k = data[i];
		data[i] = data[j];
		data[j] = k;
	}

	public static Sprite MergeWidth(Sprite[] imgs) {
		int width = 0, height = getMaxHeight(imgs);
		for (int i = 0; i < imgs.length; i++) {
			width += imgs[i].width;
		}
		System.out.println("width: " + width + ", height: " + height);
		int[] pixels = new int[width * height];
		int offX = 0;
		int offY = 0;
		for (int i = 0; i < imgs.length; i++) {
			for (int x = 0; x < imgs[i].width; x++) {
				for (int y = 0; y < imgs[i].height; y++) {
					int pixel = imgs[i].getPixels()[x + y * imgs[i].width];
					pixels[x + offX + y * width] = pixel;
				}
			}
			offX += imgs[i].width;
		}
		return new Sprite(pixels, width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public static Sprite MergeHeight(Sprite[] imgs) {
		int width = getMaxWidth(imgs);
		int height = 0;
		for (int i = 0; i < imgs.length; i++) {
			height += imgs[i].height;
		}
		int[] pixels = new int[width * height];
		System.out.println("width: " + width + ", height: " + height + ", pixels: " + pixels.length);
		int offX = 0;
		int offY = 0;
		for (int i = 0; i < imgs.length; i++) {
			for (int x = 0; x < imgs[i].width; x++) {
				for (int y = 0; y < imgs[i].height; y++) {
					int pixel = imgs[i].getPixels()[x + y * imgs[i].width];
					int yy = y + offY;
					pixels[x + yy * width] = pixel;
				}
			}

			offY += imgs[i].height;

		}
		return new Sprite(pixels, width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public static int getMaxHeight(Sprite[] imgs) {
		int result = 0;
		for (int i = 0; i < imgs.length; i++) {
			if (imgs[i].height > result) result = imgs[i].height;
		}
		return result;
	}

	public static int getMaxWidth(Sprite[] imgs) {
		int result = 0;
		for (int i = 0; i < imgs.length; i++) {
			if (imgs[i].width > result) result = imgs[i].width;
		}
		return result;
	}

	private void load() {
		try {
			this.image = ImageIO.read(getClass().getResourceAsStream(path));
			this.imageType = image.getType();
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.pixels = new int[width * height];
			this.image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			System.err.println("Failed to load image from: " + path);
		}
	}

	public void save(String dir, String name) {
		if (getImage() == null || dir == null || name == null) return;
		try {
			if (name.contains(".png")) {
				File f = new File(dir);
				if (f.mkdir() || f.exists()) {
					ImageIO.write(getImage(), "png", new File(f, "/" + name));
					System.out.println("Successfully writed new image with png!: " + dir + "/" + name);
				}
			} else {
				File f = new File(dir);
				if (f.mkdir() || f.exists()) {
					ImageIO.write(getImage(), "png", new File(dir + "/" + name + ".png"));
					System.out.println("Successfully writed new image!: " + dir + "/" + name + ".png");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Sprite(" + "Path = " + path + ", Width = " + width + ", Height = " + height + ", Pixels = " + pixels.length + ", Image = " + image + ")";
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

	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @return the imageType
	 */
	public int getImageType() {
		return imageType;
	}

	/**
	 * @return the pixels
	 */
	public int[] getPixels() {
		return pixels;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
}
