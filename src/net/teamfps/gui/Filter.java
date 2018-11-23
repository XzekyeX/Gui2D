package net.teamfps.gui;

/**
 * 
 * @author Zekye
 *
 */
public enum Filter {
	BLUR_3X3(new double[] { 0.0, 0.2, 0.0, 0.2, 0.2, 0.2, 0.0, 0.2, 0.0 }, 3, 3, 1.0D, 0.0D), //
	BLUR_5X5(new double[] { 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0 }, 5, 5, 1.0D / 13.0D, 0.0D), //
	MOTION_BLUR(new double[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, }, 9, 9, 1.0D / 9.0D, 0.0D), //
	FIND_EDGES(new double[] { 0, 0, -1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, }, 5, 5, 1.0D, 0.0D), //
	EMBOSS_3x3(new double[] { -1, -1, 0, -1, 0, 1, 0, 1, 1 }, 3, 3, 1.0D, 128.0D), //
	EMBOSS_5x5(new double[] { -1, -1, -1, -1, 0, -1, -1, -1, 0, 1, -1, -1, 0, 1, 1, -1, 0, 1, 1, 1, 0, 1, 1, 1, 1 }, 5, 5, 1.0D, 128.0D), //
	MEAN_AND_MEDIAN(new double[] { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, 3, 3, 1.0D / 9.0D, 0.0D); //

	private double[] filter;
	private int w, h;
	private double factor, bias;

	private Filter(double[] filter, int w, int h, double factor, double bias) {
		this.filter = filter;
		this.w = w;
		this.h = h;
		this.factor = factor;
		this.bias = bias;
	}

	public double[] getFilter() {
		return filter;
	}

	public double getFactor() {
		return factor;
	}

	public double getBias() {
		return bias;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

}
