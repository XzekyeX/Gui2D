/**
 * 
 */
package net.teamfps.gui.math;

/**
 * @author Zekye
 *
 */
public class Vec2i {
	protected int x, y;

	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void set(int x, int y) {
		this.x = (x);
		this.y = (y);
	}

	public void add(int x, int y) {
		this.x += (x);
		this.y += (y);
	}

	public void sub(int x, int y) {
		this.x -= (x);
		this.y -= (y);
	}

	public void div(int x, int y) {
		this.x /= (x);
		this.y /= (y);
	}

	public void mul(int x, int y) {
		this.x *= (x);
		this.y *= (y);
	}

	public boolean equals(Vec2i v) {
		return (x == v.x && y == v.y);
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
