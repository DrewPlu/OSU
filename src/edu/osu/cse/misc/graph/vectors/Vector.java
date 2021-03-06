package edu.osu.cse.misc.graph.vectors;

import java.awt.Graphics2D;
import java.awt.Point;

import edu.osu.cse.misc.graph.plotting._3d.Point3D;
import edu.osu.cse.misc.graph.plotting.wrappers.graph._2d.Coordinate2D;
import edu.osu.cse.misc.graph.plotting.wrappers.graph._2d.GraphPanel2D;

/**
 * Represents a magnitude and direction in Three-Dimensional space.
 */
public class Vector {

	public static final Vector ZERO_VECTOR = new Vector(0f, 0f, 0f);

	public float x, y, z;

	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector(float x, float y) {
		this(x, y, 0);
	}

	/**
	 * Calculates the magnitude of this vector by taking the sqrt of the sum of each term squared.
	 * 
	 * @return The resulting magnitude of this Vector.
	 */
	public float getMagnitude() {
		return (float) Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
	}

	/**
	 * Determines whether another vector is orthogonal (perpendicular) to this Vector.
	 * <br />Two vectors are orthogonal if and only if their dot product is equal to 0.
	 * 
	 * @param other A Vector.
	 * @return <tt>true</tt> or <tt>false</tt> whether the two vectors are orthogonal or not.
	 */
	public boolean isOrthogonalTo(Vector other) {
		return Vectors.dot(this, other) == 0;
	}

	/**
	 * Determines whether another vector is parallel to this Vector.
	 * <br />Two vectors are parallel if they are scalar multiples of the same unit vector.
	 * <br />Mathematically speaking, this occurs as well when the cross product of the two vectors is 0.
	 * 
	 * @param other A Vector.
	 * @return <tt>true</tt> or <tt>false</tt> whether the two vectors are parallel or not.
	 */
	public boolean isParallelTo(Vector other) {
		return Vectors.cross(this, other).equals(Vector.ZERO_VECTOR);
	}

	/**
	 * Converts this Vector into a Vector with the same direction, but a magnitude of 1.
	 * 
	 * @return A new Vector representing the same direction and magnitude of 1.
	 */
	public Vector toUnitVector() {
		return Vectors.divide(this, this.getMagnitude());
	}

	@Override
	public String toString() {
		return "<" + this.x + ", " + this.y + ", " + this.z + ">";
	}

	/**
	 * Auto-Generated
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Float.floatToIntBits(this.x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Float.floatToIntBits(this.y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Float.floatToIntBits(this.z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public void draw2D(Graphics2D g, GraphPanel2D graph) {
		this.draw2D(g, Vector.ZERO_VECTOR, graph);
	}

	public void draw2D(Graphics2D g, Vector from, GraphPanel2D graph) {
		if (this.z != 0D || from.z != 0D) {
			System.err.println("Unable to draw a vector with three dimensions in a two dimensional coordinate system at this time.");
			return;
		}
		Vector net = Vectors.sum(from, this);
		Point3D endPoint = new Point3D(net.x, net.y, net.z);

		Point fromCoord = graph.convertToScreen(new Coordinate2D((int) from.x, (int) from.y));
		Point toCoord = graph.convertToScreen(new Coordinate2D((int) endPoint.x, (int) endPoint.y));

		g.drawLine(fromCoord.x, fromCoord.y, toCoord.x, toCoord.y);
	}


	/**
	 * Compares this Vector to another.
	 * @param obj Another Object to compare this Vector to.
	 * @return <tt>true</tt> if the provided Object is a Vector, and x, y, and z are equal for each vector. <tt>false</tt> otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vector)) {
			return false;
		}
		Vector other = (Vector) obj;
		return Float.floatToIntBits(this.x) == Float.floatToIntBits(other.x)
				&& Float.floatToIntBits(this.y) == Float.floatToIntBits(other.y)
				&& Float.floatToIntBits(this.z) == Float.floatToIntBits(other.z);

	}



}
