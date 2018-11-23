/**
 * 
 */
package net.teamfps.gui.math;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class Vec2d {
	protected double x, y;

	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(double x, double y) {
		this.x = (x);
		this.y = (y);
	}

	public void add(double x, double y) {
		this.x += (x);
		this.y += (y);
	}

	public void sub(double x, double y) {
		this.x -= (x);
		this.y -= (y);
	}

	public void div(double x, double y) {
		this.x /= (x);
		this.y /= (y);
	}

	public void mul(double x, double y) {
		this.x *= (x);
		this.y *= (y);
	}

	public boolean equals(Vec2d v) {
		return (x == v.x && y == v.y);
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vec2(" + getX() + "," + getY() + ")";
	}
}
