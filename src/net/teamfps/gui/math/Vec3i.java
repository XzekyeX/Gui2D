/**
 * 
 */
package net.teamfps.gui.math;

/**
 * 
 * @author Mikko Tekoniemi
 *
 */
public class Vec3i {
	protected int x, y, z;

	public Vec3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void sub(int x, int y, int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}

	public void div(int x, int y, int z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
	}

	public void mul(int x, int y, int z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
	}

	public boolean equals(Vec3i v) {
		return (x == v.x && y == v.y && z == v.z);
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

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vec3i(" + getX() + "," + getY() + "," + getZ() + ")";
	}
}
