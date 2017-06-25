package org.aditya.catapult.util;

/*
 * utility class to map colors to trajectories
 */
public class Trajectory {
	double angle;
	double power;
	int color;
	double rotationAngle;

	public Trajectory(double angle, double power, int color, double rotationAngle) {
		super();
		this.angle = angle;
		this.power = power;
		this.color = color;
		this.rotationAngle = rotationAngle;
	}

	public double getAngle() {
		return angle;
	}

	public double getPower() {
		return power;
	}

	public int getColor() {
		return color;
	}

	public double getRotationAngle() {
		return rotationAngle;
	}
	
}
