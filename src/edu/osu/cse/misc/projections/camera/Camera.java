package edu.osu.cse.misc.projections.camera;

import java.awt.Point;

import edu.osu.cse.misc.graph.plotting._3d.Point3D;
import edu.osu.cse.misc.math.matrices.Matrices;
import edu.osu.cse.misc.math.matrices.Matrix;
import edu.osu.cse.misc.math.matrices.euler.EulerMatrices;

public class Camera extends Point3D {

	private static final float TWOPI = (float) Math.PI * 2f;
	
	private float nearDistance = 400;
	
	public float rotationYaw = 0f, rotationPitch = 0f, rotationRoll = 0f;
	
	public Camera() {
		this(0f, 0f, 1f);
	}
	
	public Camera(float x, float y, float z) {
		super(x, y, z);
	}
	
	public float getViewingDistance() {
		return this.nearDistance;
	}
	
	public void setViewingDistance(float viewingDistance) {
		this.nearDistance = viewingDistance;
		if (this.nearDistance < 0)
			this.nearDistance = 0;
	}
	
	private void adjustRotations() {
		if (this.rotationYaw < 0f)
			this.rotationYaw = TWOPI;
		if (this.rotationPitch < 0f)
			this.rotationPitch = TWOPI;
		if (this.rotationRoll < 0f)
			this.rotationRoll = TWOPI;
	}
	
	public Point project(Point3D p) {
		this.adjustRotations();
		Matrix<Float> pointMatrix = new Matrix<Float>(new Float[][] { {p.x}, {p.y}, {p.z} });
		
		Matrix<Float> xyRotation = Matrices.multiply(EulerMatrices.eulerXMatrix(this.rotationYaw), EulerMatrices.eulerYMatrix(this.rotationPitch));
		Matrix<Float> rotation = Matrices.multiply(xyRotation, EulerMatrices.eulerZMatrix(this.rotationRoll));
		
		Point3D rotated = Matrices.convertToPoint3D(Matrices.multiply(rotation, pointMatrix));
		float x = rotated.x - this.x;
		float y = rotated.y - this.y;
		float z = rotated.z - this.z;
		
		float _x = x * (this.getViewingDistance() / z);
		float _y = y * (this.getViewingDistance() / z);
		return new Point((int) _x, (int) _y);
	}
	
}
