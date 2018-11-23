/**
 * 
 */
package net.teamfps.gui.math;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class Vec2f {
	protected float x, y;

	public Vec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(float x, float y) {
		this.x = (x);
		this.y = (y);
	}

	public void add(float x, float y) {
		this.x += (x);
		this.y += (y);
	}

	public void sub(float x, float y) {
		this.x -= (x);
		this.y -= (y);
	}

	public void div(float x, float y) {
		this.x /= (x);
		this.y /= (y);
	}

	public void mul(float x, float y) {
		this.x *= (x);
		this.y *= (y);
	}

	public boolean equals(Vec2f v) {
		return (x == v.x && y == v.y);
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
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
